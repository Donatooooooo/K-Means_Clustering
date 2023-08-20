package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
	public void fromFile() {
		Page.loadPage("file.fxml", "UI client: lettura dei file", file);
	}

	/**
	 * Apre la pagina della scoperta dal database.
	 */
	@FXML
	public void fromDB() {
		Page.loadPage("db.fxml", "UI client: scoperta da DB", DB);
	}
}
