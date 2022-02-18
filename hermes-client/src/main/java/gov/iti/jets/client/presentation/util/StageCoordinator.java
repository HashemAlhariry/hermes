package gov.iti.jets.client.presentation.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public enum StageCoordinator {

	INSTANCE;

	private final Map<String, Scene> sceneMap = new HashMap<>();
	private Stage primaryStage;

	private StageCoordinator() {

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void initStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void switchToLoginScene() {
		prepareScene("loginScene", "/views/login/LoginView.fxml");
	}

	public void switchtoHomePageScene() {
		prepareScene("homepagaScene", "/views/homepage/HomePageView.fxml");
	}

	public void switchToRegisterationScene() {
		prepareScene("registerationScene", "/views/registeration/RegisterationView.fxml");
	}

	public void switchToProfileScene() {
		prepareScene("profileScene", "/views/profile/ProfileView.fxml");
	}

	private void prepareScene(String sceneName, String fxmlLocation) {
		if (sceneMap.get(sceneName) == null) {
			try {
				Pane root = FXMLLoader.load(getClass().getResource(fxmlLocation));
				Scene scene = new Scene(root);
				sceneMap.put(sceneName, scene);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		primaryStage.setScene(sceneMap.get(sceneName));
	}

	public void switchToContactScene() {
		prepareScene("contactScene", "/views/contact/ContactView.fxml");
	}

}