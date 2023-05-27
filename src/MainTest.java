import data.*;
import java.io.*;
import mining.KMeansMiner;
import keyboardinput.Keyboard;


public class MainTest {

	public static void main(String[] args) {
		
		KMeansMiner kmeans;
		Data data = null;
		int k = 0;
		int numIter = 0;
		String command;
		String server, db, tab, user, psw;
			
		while (true) {
			System.out.println("\nInserisci il comando\n");
			command = Keyboard.readString();
			
			switch(command) {
			
			case "nuovo kmeans":
				System.out.println("Connessine ad un DB speciifco?");
				command = Keyboard.readString();
				try {
					if (command.equals("si"))
					{
						System.out.print("Inserisci il nome del server (ad esempio localhost): ");
				          server = Keyboard.readString();
				          System.out.print("Inserisci il nome del database (ad esempio MapDB): ");
				          db = Keyboard.readString();
				          System.out.print("Inserisci il nome della tabella (ad esempio playtennis): ");
				          tab = Keyboard.readString();
				          System.out.print("Inserisci il nome dell'utente (ad esempio MapUser): ");
				          user = Keyboard.readString();
				          System.out.print("Inserisci la password dell'utente (ad esempio map): ");
				          psw = Keyboard.readString();
				          data = new Data(server, db, tab, user, psw);
					} else if (command.equals("no")) {
						data = new Data("localhost", "MapDB", "playtennis", "MapUser", "map");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				System.out.println(data);
				do {
					System.out.println("\nInserisci il numero di cluster (tra 1 e 14) che verranno generati: \n");			
					k=Keyboard.readInt();																//numero di centroidi
					try {
						kmeans=new KMeansMiner(k);												//oggetto kmeans
						numIter=kmeans.kmeans(data);											//richiama il metodo kmeans
					} catch (OutOfRangeSampleSize e) {
						System.out.println(e.getMessage());
					    continue;
					}
					System.out.println("Numero di Iterazione:"+numIter);
					System.out.println(kmeans.getC().toString(data));
					
					System.out.println("\nVuoi salvare i cluster generati?");
					do {
						command = Keyboard.readString();
					} while ((!command.equals("si") && !command.equals("no")));
					if(command.equals("si")) {
						try {
	                        kmeans.SaveKMeansMiner("kmeans.dat");
	                    } catch (FileNotFoundException e) {
	                        System.err.println("\nfile non trovato\n");
	                        System.exit(0);
	                    } catch (IOException e) {
	                        System.err.println("\nErrore di input\n");
	                        System.exit(0);
	                    }
					}
					System.out.println("Ripetere il processo? Y -yes | N -no");
					do {
						command = Keyboard.readString();
					} while ((!command.equals("si") && !command.equals("no")));
				 } while (command.equals("si"));
				break;
			
			case "leggi kmeans":
				try {
                    kmeans = new KMeansMiner("kmeans.dat");
                    System.out.println(kmeans.getC().toString(data));
                } catch (FileNotFoundException e) {
                    System.err.println("FILE NON TROVATO");
                    System.exit(0);
                } catch (IOException e) {
                    System.err.println("ERRORE DI INPUT/OUTPUT");
                    System.exit(0);
                } catch (ClassNotFoundException e) {
                    System.err.println("ERRORE DI CLASSE");
                    System.exit(0);
                }
				break;
			
			case "esci":
				System.out.println("Bye");
				System.exit(0);
				break;
			
			case "help":
				System.out.println("leggi kmenas = legge kmeans da file\n"
						+ "  nuovo kmeans = calcola un nuovo kmenas\n"
						+ "    esci = esce dall'app");
			
			}	
		}
	}
}
