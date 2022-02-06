package gov.iti.jets.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
		System.out.println("Hello from client");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox box=new VBox();
		primaryStage.setScene(new Scene(box));
		primaryStage.setTitle("hermes-client");
		primaryStage.show();	
	}
}