package gov.iti.jets.client.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Client;
import common.business.services.Mapper;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import common.business.services.Client;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.Utils;

public class ClientImpl extends UnicastRemoteObject implements Client {


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
	public void loginSuccess(UserDto userDto) {
		ModelsFactory modelsFactory = ModelsFactory.INSTANCE;
		UserModel userModel = MapperImpl.INSTANCE.mapFromUserDto(userDto);
		System.out.println("User Login Successfully");
	}

	@Override
	public void registerationSuccess() throws RemoteException {
		System.out.println("User Registered Succefully");
	}

}
