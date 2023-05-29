package server;

import data.Data;
import data.OutOfRangeSampleSize;
import mining.KMeansMiner;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

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
			int command = (Integer) in.readObject();
			Data data = new Data("localhost", "MapDB", "playtennis", "MapUser", "map");
			switch (command) {
			case 0:
				tabName = (String) in.readObject();
				System.out.println(tabName);
				//fa ciò che deve fare
				out.writeObject("OK");
				break;
			case 1:
				k = (Integer) in.readObject();
				int numIter = 0;
				try {
					kmeans=new KMeansMiner(k);
					numIter=kmeans.kmeans(data);
				} catch (OutOfRangeSampleSize e) {
					System.out.println(e.getMessage());
				}
				out.writeObject("OK");
				out.writeObject("Numero di iterazione: " + numIter + "\n" + kmeans.getC().toString(data));
				break;
			case 2:
				try {
					System.out.println("AAAA");
                    kmeans.SaveKMeansMiner("kmeans.dat");
                    out.writeObject("OK");
                } catch (FileNotFoundException e) {
                    System.err.println("\nfile non trovato\n");
                    break;
                } catch (IOException e) {
                    System.err.println("\nErrore di input\n");
                    break;
                }
				break;
			case 3:
				tabName = in.readObject().toString();
				k = (Integer) in.readObject();
				//fa ciò che deve fare
				out.writeObject("OK");
				out.writeObject("OKtemp");
				break;
			}
		} catch (Exception e) {
			System.out.println("Qualsiasi eccezione: " + e.getMessage());
		} finally {
			try {
				socket.close();
				System.out.println("Socket chiuso");
			} catch(IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}
