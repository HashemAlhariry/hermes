package gov.iti.jets.client.presentation.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.ResourceBundle;

import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class NextloginController implements Initializable {
	@FXML
	private PasswordField passwordTextField;

	@FXML
	private Button signInButton;

	@FXML
	private CheckBox remembermeCheckBox;

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private void initUserImage() {
		Image image;
		System.out.println("IMAGE : " + ModelsFactory.INSTANCE.getUserModel().getPicture());
		if (ModelsFactory.INSTANCE.getUserModel().getPicture() ==null) {
			try {

				byte[] imgBytes = RMIConnection.INSTANCE.getServer()
						.getUserProfilePicture(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
				System.out.println("bytes : " + imgBytes);
				if (imgBytes != null) {
					image = Util.INSTANCE.fromArrayOfBytesToImage(imgBytes);
					ModelsFactory.INSTANCE.getUserModel().setPicture(image);
				}

			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void signinButtonAction(ActionEvent event) {
		try {
			ModelsFactory.INSTANCE.getUserModel().setPassword(passwordTextField.getText());
			UserAuthDto userAuthDto = MapperImpl.INSTANCE.mapToUserAuthDto(ModelsFactory.INSTANCE.getUserModel());
			UserDto userDto = RMIConnection.INSTANCE.getServer().login(ServiceFactory.INSTANCE.getClientImpl(),
					userAuthDto);
			if (userDto != null) {
				if (remembermeCheckBox.isSelected()) {
					Platform.runLater(() -> {
						rememberCredentials(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(),
								userAuthDto.password);
					});
				}
				var newUserModel = MapperImpl.INSTANCE.mapFromUserDto(userDto);
				ModelsFactory.INSTANCE.setUserModel(newUserModel);
				initUserImage();
				stageCoordinator.switchtoHomePageScene();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Hermes");
				alert.setHeaderText(null);
				alert.setContentText("Wrong Password");
				alert.showAndWait();
			}

		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void signinKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			stageCoordinator.switchToLoginScene();
		}
	}

	private void rememberCredentials(String phone, String password) {
		byte[] creds = (phone + "\n" + password).getBytes();
		var encoder = Base64.getEncoder();
		byte[] encoded = encoder.encode(creds);
		try {
			Files.write(Paths.get("creds"), encoded, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
