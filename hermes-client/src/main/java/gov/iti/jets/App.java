package gov.iti.jets;

import gov.iti.jets.presentation.util.StageCoordinator;
import javafx.application.Application;
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
		primaryStage.show();
	}
}