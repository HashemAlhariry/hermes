package gov.iti.jets.client;

import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void init() throws Exception {
		super.init();
		RMIConnection.INSTANCE.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageCoordinator.initStage(primaryStage);
		stageCoordinator.switchToLoginScene();
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		primaryStage.setTitle("Hermes");
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		RMIConnection.INSTANCE.close();
		ServiceFactory.INSTANCE.releaseClientImpl();
		super.stop();
		/*
		/ IMPORTANT: Due to the fact that client has no mechanism to close all the underlying
		/ connections, and the connections are pooled and closed fairly aggressively anyway by
		/ the jvm but in a non determined duration (Which is unacceptable behaviour), after many
		/ tries to manually set references to null and calling System.gc() explicitly and memory
		/ monitoring through heap dumps to make sure that the client holds no reference  to the
		/ stub-object from the rmiregistry, there was only one option that is to force the client's
		/ JVM to close using System.exit(0) and considering it a normal terminataion. 
		*/
		System.exit(0);
	}

}