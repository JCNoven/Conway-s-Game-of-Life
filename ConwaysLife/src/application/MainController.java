package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Classes.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button play = new Button();
	
	public void start(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Draw.fxml"));
		root = loader.load();
		
		DrawController DrawController = loader.getController();
		DrawController.updateViewBoard();
		DrawController.startOver(event);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	

}
