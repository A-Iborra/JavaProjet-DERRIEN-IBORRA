// Projet JAVA - M1 Informatique //
// Auteur :DERRIEN Brice et IBORRA Alexandre //
// Création d'une application pour gérer une base de tweet

package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

//Création de la classe Main
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Fouille de texte"); //Titre de la fenetre
			primaryStage.setScene(Scene());
			primaryStage.show(); //Création de la fenetre
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Scene Scene() throws IOException
	{
		Parent root=FXMLLoader.load(getClass().getResource("main.fxml")); //Appel du fichier FXML  
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //Appel du CSS
	return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
