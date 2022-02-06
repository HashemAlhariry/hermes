package gov.iti.jets.client.presentation.util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public enum StageCooridinator {

    INSTANCE;

    private Stage primaryStage;

    private final Map<String, Scene> sceneMap = new HashMap<>();

    public void initStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	// Examples on how you may implement switching to different scenes

    // public void switchToLoginScene() {
    //     prepareScene("loginScene", "/layout/login/LoginLayout.fxml");
    // }

	// public void switchToRegisterScene() {
    //     prepareScene("loginScene", "/layout/register/RegisterLayout.fxml");
    // }

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

}
