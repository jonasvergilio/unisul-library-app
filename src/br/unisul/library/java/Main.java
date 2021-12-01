package br.unisul.library.java;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/br/unisul/library/resources/view/Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/br/unisul/library/resources/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Biblioteca");
			primaryStage.setMaximized(true);
			primaryStage.getIcons().add(new Image("/br/unisul/library/resources/imgs/book-icon.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
