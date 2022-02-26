package gov.iti.jets.client.presentation.controllers;

import java.net.URL;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import common.business.dtos.UserDto;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController implements Initializable {

	@FXML
	private TextField phoneTextField;

	@FXML
	private Button nextButton;

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
