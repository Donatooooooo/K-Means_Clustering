package application;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.SocketException;

import client.ServerException;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Classe che controlla la pagina "db.fxml".
 */
public class dbController {
	
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
     * Pulsante per avviare loadFromDB().
     */
    @FXML
    private Button cluster;
    
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
     * Esegue la clusterizzazione dal DB.
     */
    @FXML
    private void loadFromDB() {
    	try {
			StartController.getClient().storeTableFromDb(tabName.getText());
			int k = Integer.parseInt(nCluster.getText());
			String res = StartController.getClient().learningFromDbTable(k);
			result.setText(res);
    	} catch (SocketException e) {
    		Page.showAlert(e.getMessage());
    	} catch (ClassNotFoundException e) {
    		Page.showAlert(e.getMessage());
    	} catch (IOException e) {
    		Page.showAlert(e.getMessage());
    	} catch (ServerException e) {
    		Page.showAlert(e.getMessage());
    	} catch (NumberFormatException e) {
    		Page.showAlert("--- Valore cluster non valido, inserisci un numero ---");
    	}
    }

    /**
     * Richiama il caricamento alla pagina del menu.
     */
    @FXML
    private void backtoMenu() {
    	Page.loadPage("Menu.fxml", "UI client: menu", back);
    }

}
