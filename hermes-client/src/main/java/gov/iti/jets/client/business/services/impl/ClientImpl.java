package gov.iti.jets.client.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.business.dtos.MessageDto;
import common.business.services.Client;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Utils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientImpl extends UnicastRemoteObject implements Client {

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	public ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void recieveMessage(MessageDto message) {
		System.out.println("MESSAGE RECIEVED" + message.content);
	}

	@Override
	public void recieveInvitation(String sender) {
		System.out.println("INVITATION RECIEVED FROM " + sender);
		Utils.INSTANCE.invitationNotification(sender);
	}

	@Override
	public String getPhoneNumber() {
		return ModelsFactory.INSTANCE.getUserModel().getPhoneNumber();
	}

	@Override
	public void loginSuccess() {

	}

	@Override
	public void registerationSuccess() throws RemoteException {

		Platform.runLater(stageCoordinator::switchtoHomePageScene);
		System.out.println("User Registered Succefully");
	}

	@Override
	public void registerationFail(String errorMessage) throws RemoteException {
		Platform.runLater(()->{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(errorMessage);
			alert.show();
		});
	}

}
