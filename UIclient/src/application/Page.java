package application;

import java.io.IOException;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.fxml.FXMLLoader;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Classe che contiene metodi statici per il caricamento delle pagine dell'UI.
 */
public class Page {
	
	/**
	 * metodo che carica e mostra la pagina principale.
	 * @param primaryStage finestra principale su cui caricare la pagina UI.
	 */
	static void loadPage(Stage primaryStage) {
		try {
			Page p = new Page();
			Parent root = FXMLLoader.load(p.getClass().getResource("../UI/Start.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("UI client");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(IOException e) {
			System.err.println("--- Errore nell'apertura di: Start.fxml ---");
		}
	}
	
	/**
	 * carica e mostra le pagine secondarie a seconda della chiamata.
	 * @param fileName nome del file da caricare.
	 * @param title titolo della finestra.
	 * @param o oggetto su cui verrà applicato RTTI per ottenere le informazioni sul tipo di finestra.
	 */
	static void loadPage(String fileName, String title, Object o) {
		try {
			Page p = new Page();
            FXMLLoader fxmlLoader = new FXMLLoader(p.getClass().getResource("../UI/" + fileName));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            Stage stage;
            
            if (o instanceof Button) {
            	Button b = (Button) o;
            	stage = (Stage) b.getScene().getWindow();
            } else  {
            	ProgressBar pb = (ProgressBar) o;
            	stage = (Stage) pb.getScene().getWindow();
            }

            stage.setScene(newScene);
			stage.setTitle(title);
        } catch (IOException e) {
        	System.err.println("--- Errore nell'apertura di: " + fileName + " ---");
        }
	}
	
	static void showAlert(String msg) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Errore");
	    alert.setHeaderText(null);
	    alert.setContentText(msg);
	    alert.showAndWait();
	}
	
}
