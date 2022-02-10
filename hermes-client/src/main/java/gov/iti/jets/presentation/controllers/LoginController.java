package gov.iti.jets.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import gov.iti.jets.presentation.models.UserModel;
import gov.iti.jets.presentation.util.ModelsFactory;
import gov.iti.jets.presentation.util.StageCoordinator;

public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private TextField userNameTextField;

    private final StageCoordinator stageCoordinator = StageCoordinator.getInstance();
    private final ModelsFactory modelsFactory = ModelsFactory.getInstance();

    @FXML
    void onLoginButtonClick(ActionEvent event) {

    }

    @FXML
    void onregisterationButtonClick(ActionEvent event) {
        stageCoordinator.switchToregisterationScene();
    }

    @FXML
    void onProfileButtonClick(ActionEvent event) {
        stageCoordinator.switchtoHomePageScene();

    }

    @FXML
    void onHomePageButtonClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UserModel userModel = modelsFactory.getUserModel();
        userNameTextField.textProperty().bindBidirectional(userModel.userNameProperty());
        passwordPasswordField.textProperty().bindBidirectional(userModel.passwordProperty());

    }

}
