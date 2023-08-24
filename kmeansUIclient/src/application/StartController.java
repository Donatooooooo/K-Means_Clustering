package application;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.IOException;

import client.Client;
import javafx.application.Platform;

/**
 * Classe che controlla la pagina "Start.fxml".
 */
public class StartController {

	/**
	 * Barra di progresso.
	 */
    @FXML
    private ProgressBar bar;
    
    /**
     * Immagine visualizzata nel caricamento.
     */
    @FXML
    private ImageView img;

    /**
     * Oggetto client che permette il clustering.
     */
    private static Client client;

    /**
     * Consente di operare con il client
     * @return client oggetto di tipo client.
     */
    static Client getClient() {
    	return client;
    }

    /**
     * Avvia il caricamento della barra all'apertura del programma. In caso 
     * il collegamento al server fallisce, chiude l'app.
     */
    @FXML
    public void initialize() {
    	try {
    		client = new Client();
    	} catch (IOException e) {
    		Paging.showAlert("Connessione al server fallita, assicurati che sia online");
    		System.exit(0);
    	}
		loadStartingBar();
    }

    /**
     * Carica la barra, al termine chiama l'apertura del menu.
     */
    private void loadStartingBar() {
        bar.setProgress(0);

        new Thread(() -> {
            for (double p = 0; p <= 1; p += 0.01) {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    return;
                }
                updateBar(p);
            }
            Platform.runLater(this::openMenu);
        }).start();
    }

    /**
     * Aggiorna la barra in base al progresso.
     * @param progress nuovo progresso da aggiungere.
     */
    private void updateBar(double progress) {
        Platform.runLater(() -> bar.setProgress(progress));
    }

    /**
     * Apre il menu principale.
     */
    private void openMenu() {
    	Paging.loadPage("Menu.fxml", "Menu", bar);
    }
}
