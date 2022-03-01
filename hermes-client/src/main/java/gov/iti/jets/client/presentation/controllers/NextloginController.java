package gov.iti.jets.client.presentation.controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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

	}

	@FXML
	void signinButtonAction(ActionEvent event) {
		try {
			ModelsFactory.INSTANCE.getUserModel().setPassword(passwordTextField.getText());
			// sending raw password to the server unitl secure password hashing is fixed
			UserAuthDto userAuthDto = MapperImpl.INSTANCE.mapToUserAuthDto(ModelsFactory.INSTANCE.getUserModel());
			System.out.println(passwordTextField.getText());
			System.out.println(userAuthDto.password);
			System.out.println(ModelsFactory.INSTANCE.getUserModel().getPassword());
			UserDto userDto = RMIConnection.INSTANCE.getServer().login(ServiceFactory.INSTANCE.getClientImpl(),
					userAuthDto);
			if (userDto != null) {
				System.out.println(userDto.bio);
				var newUserModel = MapperImpl.INSTANCE.mapFromUserDto(userDto);
				ModelsFactory.INSTANCE.setUserModel(newUserModel);
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
