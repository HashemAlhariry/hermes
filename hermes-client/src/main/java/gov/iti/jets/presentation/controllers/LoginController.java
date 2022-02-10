package gov.iti.jets.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.jets.presentation.util.ModelsFactory;
import gov.iti.jets.presentation.util.StageCoordinator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	@FXML
	private PasswordField passwordPasswordField;

	@FXML
	private TextField userNameTextField;

	private final StageCoordinator stageCoordinator = StageCoordinator.getInstance();
	private final ModelsFactory modelsFactory = ModelsFactory.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void onRegisterationButtonClick(ActionEvent event) {
		stageCoordinator.switchToRegisterationScene();
	}

	@FXML
	void onLoginButtonClick(ActionEvent event) {

	}

	@FXML
	void onProfileButtonClick(ActionEvent event) {
		stageCoordinator.switchtoHomePageScene();

	}

	@FXML
	void onHomePageButtonClick(ActionEvent event) {

	}

}