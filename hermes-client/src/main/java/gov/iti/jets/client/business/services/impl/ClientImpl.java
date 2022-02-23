package gov.iti.jets.client.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.business.dtos.MessageDto;
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
	public void loginSuccess() {
		 
	}

	@Override
	public void registerationSuccess() throws RemoteException {
		System.out.println("User Registered Succefully");
	}

}
