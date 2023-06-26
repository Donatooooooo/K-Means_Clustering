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
	
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}
	
	public void run() {
		try {
			int k;
			String tabName;
			KMeansMiner kmeans = null;
			Data data = null;
			while (true) {
				int command = (Integer) in.readObject();
				switch (command) {
				case 0:
					tabName = (String) in.readObject();
					try {
						data = new Data("localhost", "MapDB", tabName, "MapUser", "map");
						out.writeObject("OK");
					} catch (final EmptySetException e) {
						out.writeObject("\n--- Tabella vuota ---\n");
					} catch (SQLException e) {
						out.writeObject("\n--- Tabella non trovata ---\n");
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
						out.writeObject("OK");
						out.writeObject(numIter);
						out.writeObject("\n" + kmeans.getC().toString(data));
					} catch (OutOfRangeSampleSize e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 2:
					try {
	                    kmeans.SaveKMeansMiner("kmeans.dat");
	                    out.writeObject("OK");
	                } catch (FileNotFoundException e) {
	                	out.writeObject("\n--- File non trovato ---\n");
	                } catch (IOException e) {
	                	out.writeObject("\n--- Errore di input ---\n");
	                }
					break;
				case 3:
					tabName = (String) in.readObject();
					try {
						data = new Data("localhost", "MapDB", tabName, "MapUser", "map");
						kmeans = new KMeansMiner("kmeans.dat");
		                out.writeObject("OK");
						out.writeObject(kmeans.getC().toString(data));
					} catch (EmptySetException e) {
						out.writeObject("\n--- Tabella vuota ---\n");
					} catch (SQLException e) {
						out.writeObject("\n--- Tabella non trovata ---\n");
					} catch (DatabaseConnectionException | NoValueException e) {
						out.writeObject(e.getMessage());
					} catch (FileNotFoundException e) {
	                	out.writeObject("\n--- File non trovato ---\n");
	                } catch (IOException e) {
	                	out.writeObject("\n--- Errore di input/output ---\n");
	                } catch (ClassNotFoundException e) {
	                	out.writeObject("\n--- Errore di classe ---\n");
	                }
					break;
				case 4:
					return;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e);
		} finally {
			try {
				socket.close();
				System.out.println("+++ Socket closed +++");
			} catch(IOException e) {
				System.err.println("--- Socket not closed ---");
			}
		}
	}
}
