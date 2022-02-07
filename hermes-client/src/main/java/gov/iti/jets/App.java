package gov.iti.jets;

import gov.iti.jets.presentation.util.StageCoordinator;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private StageCoordinator stageCoordinator = StageCoordinator.getInstance();

	public static void main(String[] args) {
		System.out.println("Hello from client");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

        stageCoordinator.initStage(primaryStage);

        stageCoordinator.switchToLoginScene();

        primaryStage.show();
	}
}