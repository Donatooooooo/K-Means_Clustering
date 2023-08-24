package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale di UIclient.
 */
public class Main extends Application {

	/**
	 * Richiama il caricamento della pagina principale.
	 */
	@Override
	public void start(Stage primaryStage) {
		Paging.loadPage(primaryStage);
	}

	/**
	 * Metodo main.
	 * @param args argomenti in input.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
