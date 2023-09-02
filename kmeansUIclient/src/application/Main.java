package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale di UIclient.
 */
public class Main extends Application {
	
	/**
	 * Stringa che rappresenta l'ip a cui collegarsi.
	 */
	private static String ip = "127.0.0.1";
	
	/**
	 * Intero che rappresenta la porta su cui il server è in ascolto.
	 */
	private static int port = 8080;
	
	/**
	 * Metodo che richiama il caricamento della pagina principale.
	 */
	@Override
	public void start(Stage primaryStage) {
		Paging.loadPage(primaryStage);
	}

	/**
	 * Metodo main.
	 * @param args argomenti in input (ip e port).
	 */
	public static void main(String[] args) {
		if(args.length > 0) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
		}
		launch(args);
	}
	
	/**
	 * Metodo che fornisce l'indirizzo ip.
	 * @return ip, indirizzo del server.
	 */
	static String getIp() {
		return ip;
	}
	
	/**
	 * Metodo che fornisce la porta del server.
	 * @return port, porta su cui il server è in ascolto.
	 */
	static int getPort() {
		return port;
	}
}
