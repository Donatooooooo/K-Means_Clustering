package application;

import java.io.IOException;
import java.net.SocketException;

import client.ServerException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Classe che controlla la pagina "File.fxml".
 */
public class fileController {
	
    /**
     * Campo di testo per inserire il numero di cluster.
     */
	 @FXML
	 private TextField nCluster;

    /**
     * Campo di testo per inserire il nome della tabella.
     */
    @FXML
    private TextField tabName;

    /**
     * Pulsante per avviare loadFromFile().
     */
    @FXML
    private Button load;

    /**
     * Pulsante per tornare indietro.
     */
    @FXML
    private Button back;

    /**
     * Area di testo dove visualizzare i risultati.
     */
    @FXML
    private TextArea result;

    /**
     * Inizializza result rendendolo non editabile.
     */
    @FXML
    public void initialize() {
    	result.setEditable(false);
    }

    /**
     * Esegue la lettura del file
     */
    @FXML
    private void loadFromFile() {
    	try {
    		if(tabName.getText().isEmpty() || nCluster.getText().isEmpty())
    			throw new ServerException("Inserisci dei valori");
    		int k = Integer.parseInt(nCluster.getText());
    		String res = StartController.getClient().learningFromFile(tabName.getText(), k);
        	result.setText(res);
    	} catch (SocketException e) {
    		Paging.showAlert(e.getMessage());
    	} catch (ClassNotFoundException e) {
    		Paging.showAlert(e.getMessage());
    	} catch (IOException e) {
    		Paging.showAlert(e.getMessage());
    	} catch (ServerException e) {
    		Paging.showAlert(e.getMessage());
    	} catch (NumberFormatException e) {
    		Paging.showAlert("Numero cluster non valido, scegliere un altro valore");
    	}
    	tabName.clear();
    	nCluster.clear();
    }

    /**
     * Richiama il caricamento alla pagina del menu.
     */
    @FXML
    private void backtoMenu() {
    	Paging.loadPage("Menu.fxml", "Menu", back);
    }
}
