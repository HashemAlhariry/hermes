package gov.iti.jets.client.presentation.controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.ResourceBundle;

import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Utils;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
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

	private Boolean checkServerAvailabilty = true;

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

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
		// check if there is cred file exists
		checkIfRememberedLoginActivated();
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
			Utils.INSTANCE.booleanProperty.set(RMIConnection.INSTANCE.getServer().getServerAvailability());

			if (Utils.INSTANCE.booleanProperty.get()) {
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
			} else {
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("The server is down we are sorry to inform you that !");
					alert.show();
				});
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

	private void checkIfRememberedLoginActivated() {
		try {
			if (!Paths.get("creds").toFile().exists())
				return;
			byte[] credsEncoded = Files.readAllBytes(Paths.get("creds"));
			if (credsEncoded != null) {
				var decoder = Base64.getDecoder();
				byte[] credsInBytes = decoder.decode(credsEncoded);
				String creds = new String(credsInBytes);
				if (creds.split("\n").length == 2) {
					String phone = creds.split("\n")[0];
					String password = creds.split("\n")[1];
					ModelsFactory.INSTANCE.getUserModel().setPhoneNumber(phone);
					UserDto userDto = RMIConnection.INSTANCE.getServer()
							.login(ServiceFactory.INSTANCE.getClientImpl(),
									new UserAuthDto(phone, password));
					if (userDto != null) {
						Platform.runLater(() -> {
							StageCoordinator.INSTANCE.switchtoHomePageScene();
						});
					} else {
						ModelsFactory.INSTANCE.setUserModel(new UserModel());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
