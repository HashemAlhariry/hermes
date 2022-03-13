package gov.iti.jets.client.business.services.impl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.business.dtos.GroupDto;
import common.business.dtos.GroupStatusDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserDto;
import common.business.services.Client;
import common.business.util.OnlineStatus;
import gov.iti.jets.client.presentation.controllers.Contact;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presentation.util.Utils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;

public class ClientImpl extends UnicastRemoteObject implements Client {

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	public ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void recieveMessage(MessageDto message) {
		Utils.INSTANCE.appendMessagesToVbox(message);
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
	public void loginSuccess(UserDto userDto) {
		UserModel userModel = MapperImpl.INSTANCE.mapFromUserDto(userDto);
		ModelsFactory.INSTANCE.setUserModel(userModel);
	}

	@Override
	public void registerationSuccess() throws RemoteException {

		Platform.runLater(stageCoordinator::switchtoHomePageScene);
		System.out.println("User Registered Succefully");
	}

	@Override
	public OnlineStatus getOnlineStatus() throws RemoteException {
		return ModelsFactory.INSTANCE.getUserModel().getOnlineStatus();
	}

	@Override
	public void updateContactStatus(GroupStatusDto groupStatusDto) throws RemoteException {
		ModelsFactory.INSTANCE.getContactsModel().contactsProperty().forEach((c) -> {
			if (c.groupId == groupStatusDto.id) {
				c.status = groupStatusDto.onlineStatus;
				Platform.runLater(() -> {
					c.update();
				});
			}
		});
	}

	public void registerationFail(String errorMessage) throws RemoteException {
		Platform.runLater(()->{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(errorMessage);
			alert.show();
		});
	}

	@Override
	public void receiveBroadCastMessage(String broadCastMessage) throws RemoteException {
		System.out.println(broadCastMessage);
		Utils.INSTANCE.receiveBroadCastMessage(broadCastMessage);
	}

	public void serverAvailability(boolean checkServerAvailability){
		//Send all users to loginPage/Registration page
		//disable login / registration

		if(checkServerAvailability){
			Utils.INSTANCE.booleanProperty.set(true);
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("The Server is back you can Login Again ");
				alert.show();
			});

		}else
		{

			Utils.INSTANCE.booleanProperty.set(false);
			Platform.runLater(() -> {
				stageCoordinator.switchToLoginScene();
				Alert alertBox = new Alert(AlertType.ERROR, "Announcement", ButtonType.OK);
				alertBox.setTitle("Server Announcement");
				alertBox.setContentText("We are sorry to tell you that the server is going down, Please try again later");
				alertBox.initModality(Modality.APPLICATION_MODAL);
				alertBox.initOwner( StageCoordinator.INSTANCE.getPrimaryStage());
				alertBox.showAndWait();
			});
		}


	}

    @Override
    public void addNewContactOnInvitationAcceptance(GroupDto groupDto) throws RemoteException {
		try {
			Image img  = Util.INSTANCE.fromArrayOfBytesToImage(groupDto.image);
			ModelsFactory.INSTANCE.getContactsModel().getContacts().add(new Contact(groupDto.id,groupDto.name,img,groupDto.status));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
