package server;

import data.Data;
import data.OutOfRangeSampleSize;
import mining.KMeansMiner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;


public class ServerOneClient extends Thread{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private KMeansMiner kmeans;
	
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}
	
	public void run() {
		String tabName = null;
		Data data = null;
		int k = 0;
		
		String tn = "\n[" + getName() + "] ";
		
		try {
			while (true) {
				int command = (Integer) in.readObject();
				switch (command) {
				case 0:
					tabName = (String) in.readObject();
					try {
						System.out.print(tn);
						data = new Data(tabName);
						out.writeObject("OK");
						System.out.println(tn + "Lettura tabella da DB");
					} catch (final EmptySetException e) {
						out.writeObject("\n--- Tabella vuota, sceglierne un'altra ---\n");
					} catch (SQLException e) {
						out.writeObject("\n--- Tabella non trovata, sceglierne un'altra ---\n");
					} catch (DatabaseConnectionException | NoValueException e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 1:
					k = (Integer) in.readObject();
					int numIter = 0;
					try {
						kmeans=new KMeansMiner(k);
						numIter=kmeans.kmeans(data);
						System.out.println(tn + "Clusterizzazione dati");
						out.writeObject("OK");
						out.writeObject(numIter);
						out.writeObject("\n" + kmeans.getC().toString(data));
					} catch (OutOfRangeSampleSize e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 2:
					try {
	                    kmeans.SaveKMeansMiner(tabName + "_" + k + ".dat");
	                    out.writeObject("OK");
	                    System.out.println(tn + tabName + "_" + k + ".dat: file salvato");
	                } catch (FileNotFoundException e) {
	                	out.writeObject("\n--- File non trovato ---\n");
	                } catch (IOException e) {
	                	out.writeObject("\n--- Errore di input ---\n");
	                }
					break;
				case 3:
					tabName = (String) in.readObject();
					k = (Integer) in.readObject();
					try {
						System.out.print(tn);
						data = new Data(tabName);
						kmeans = new KMeansMiner(tabName + "_" + k+ ".dat");
		                out.writeObject("OK");
						out.writeObject(kmeans.getC().toString(data));
						System.out.println(tn + tabName + "_" + k + ".dat: file letto");
					} catch (EmptySetException e) {
						out.writeObject("\n--- Tabella vuota, sceglierne un'altra ---\n");
					} catch (SQLException e) {
						out.writeObject("\n--- Tabella non trovata, sceglierne un'altra ---\n");
					} catch (DatabaseConnectionException | NoValueException e) {
						out.writeObject(e.getMessage());
					} catch (FileNotFoundException e) {
	                	out.writeObject("\n--- File non trovato ---\n");
	                } catch (IOException e) {
	                	out.writeObject("\n--- Errore di input/output ---\n");
	                } catch (ClassNotFoundException e) {
	                	out.writeObject("\n--- Classe non disponibile ---\n");
	                } 
					break;
				case 4:
					return;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			//chiusura socket attraverso finally
		} finally {
			try {
				socket.close();
				System.out.println("\n+++ [" + getName() + "] Socket chiuso +++");
			} catch(IOException e) {
				System.err.println("\n--- [" + getName() + "] Socket non chiuso ---");
			}
		}
	}
}
