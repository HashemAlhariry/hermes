package gov.iti.jets.client.presentation.controllers;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import common.business.dtos.UserDto;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController implements Initializable {

	@FXML
	private TextField phoneTextField;

	@FXML
	private Button nextButton;

	@FXML
	private ImageView eyeImage;
	@FXML
	private TextField nameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button signInButton;

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
	private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;
	private UserModel userModel = modelsFactory.getUserModel();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String lastLoggedPhone = ModelsFactory.INSTANCE.getUserModel().getPhoneNumber();
		if (lastLoggedPhone == null) {
			lastLoggedPhone = "";
			phoneTextField.setText(lastLoggedPhone);
		} else {
			phoneTextField.setText(lastLoggedPhone);
		}

		phoneTextField.setFocusTraversable(false);
	}

	@FXML
	void eyeImageMouseClicked(MouseEvent event) {

	}

	@FXML
	void signUpHyperLinkAction(ActionEvent event) {
		stageCoordinator.switchToRegisterationScene();
	}

	@FXML
	void nextButtonAction(ActionEvent event) {
		try {

			UserDto userDto = RMIConnection.INSTANCE.getServer().checkPhone(ServiceFactory.INSTANCE.getClientImpl(),
					phoneTextField.getText());
			ModelsFactory.INSTANCE.getUserModel().setPhoneNumber(phoneTextField.getText());
			if (userDto != null) {
				stageCoordinator.switchToNextLoginScene();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Hermes");
				alert.setHeaderText(null);
				alert.setContentText("Not found");
				alert.showAndWait();
			}

		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void nextKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			stageCoordinator.switchToNextLoginScene();
		}
	}

}
