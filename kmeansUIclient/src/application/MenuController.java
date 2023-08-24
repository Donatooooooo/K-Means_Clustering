package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Classe che controlla la pagina "Menu.fxml".
 */
public class MenuController {
	
	/**
	 * Tasto che richiama il metodo fromFile.
	 */
	@FXML
    private Button file;
	
	/**
	 * Tasto che richiama il metodo fromDB.
	 */
	@FXML
    private Button DB;
	
	/**
	 * Apre la pagina del caricamento da file.
	 */
	@FXML
	private void fromFile() {
		Paging.loadPage("file.fxml", "Lettura da file", file);
	}

	/**
	 * Apre la pagina della scoperta dal database.
	 */
	@FXML
	private void fromDB() {
		Paging.loadPage("db.fxml", "Scoperta da DB", DB);
	}
}