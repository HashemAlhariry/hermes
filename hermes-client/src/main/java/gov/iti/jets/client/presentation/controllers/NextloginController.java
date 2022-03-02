package gov.iti.jets.client.presentation.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class NextloginController implements Initializable {
	@FXML
	private PasswordField passwordTextField;

	@FXML
	private Button signInButton;

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			Registry registry = LocateRegistry.getRegistry();
			for (var s : registry.list()) {
				System.out.println(s);
			}

		} catch (AccessException e) {
			e.printStackTrace();

		} catch (RemoteException e) {
			e.printStackTrace();
		}
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

			UserAuthDto userAuthDto = new UserAuthDto(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(),
					passwordTextField.getText());
			UserDto userDto = RMIConnection.INSTANCE.getServer().login(ServiceFactory.INSTANCE.getClientImpl(),
					userAuthDto);
			if (userDto != null) {
				System.out.println(userDto.bio);
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
}
