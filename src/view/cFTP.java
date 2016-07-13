package view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class cFTP extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL fxml = getClass().getResource("cFTP.fxml");
		Parent parent = FXMLLoader.load(fxml);
		stage.setTitle("cFTP - JAVAFX");
		stage.setScene(new Scene(parent));
		stage.show();
	}

}