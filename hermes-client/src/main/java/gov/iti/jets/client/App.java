package gov.iti.jets.client;

import gov.iti.jets.client.presentation.util.StageCoordinator;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageCoordinator.initStage(primaryStage);
		stageCoordinator.switchToLoginScene();
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		primaryStage.setTitle("Hermes");
		primaryStage.show();
	}
}