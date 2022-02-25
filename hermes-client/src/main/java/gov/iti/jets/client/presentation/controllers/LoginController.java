package gov.iti.jets.client.presentation.controllers;

import java.io.Serializable;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.function.IntBinaryOperator;

import common.business.dtos.UserAuthDto;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable {

	@FXML
    private TextField phoneTextField;
	
	@FXML
	private Button nextButton;

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
	private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;

	private UserModel userModel = modelsFactory.getUserModel();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getFormsValues();
		
	}
	
	@FXML
	void signUpHyperLinkAction(ActionEvent event) {
		stageCoordinator.switchToRegisterationScene();
	}

	@FXML
	void nextButtonAction(ActionEvent event) {
		stageCoordinator.switchToNextLoginScene();

		

	}

	@FXML
	void nextKeyPressed(KeyEvent event) {
		if(event.getCode()==KeyCode.ENTER){
			stageCoordinator.switchToNextLoginScene();
		}
	}
	private void getFormsValues(){
		userModel.phoneNumberProperty().bindBidirectional(phoneTextField.textProperty());
	 }

	
 

}
