package gov.iti.jets.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.jets.presentation.models.UserModel;
import gov.iti.jets.presentation.util.ModelsFactory;
import gov.iti.jets.presentation.util.StageCoordinator;

public class RegisterationController implements Initializable {

    private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
    private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private DatePicker birthDateFeild;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UserModel userModel = modelsFactory.getUserModel();
        ToggleGroup toggleGendGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(toggleGendGroup);
        femaleRadioButton.setToggleGroup(toggleGendGroup);
    }

    @FXML
    void loginAction(ActionEvent event) {
        stageCoordinator.switchToLoginScene();
    }

    @FXML
    void registerationAction(ActionEvent event) {
        stageCoordinator.switchToLoginScene();
    }

}
