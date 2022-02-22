package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import common.business.dtos.InvitationDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Client;
import common.business.services.Server;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.daos.impl.GroupDaoImpl;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;

public class ServerImpl extends UnicastRemoteObject implements Server {

	private Map<String, Client> connectedClients;

	public ServerImpl() throws RemoteException {
		super();
		connectedClients = new HashMap<>();
	}

	@Override
	public void login(Client connectedClient, UserAuthDto userAuthDto) {

		// call another class for authenicating db
		// checking if user exists or not

		connectedClients.put(userAuthDto.phoneNumber, connectedClient);
		System.out.println("HELLO FROM SERVER");
		try {
			connectedClient.loginSuccess();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void register(Client connectedClient, UserDto userDto) {
		// registered user will be connected?
		// map userDto to userEntity
		// call userDao to insert user data
		UserDao userDao= DaosFactory.INSTANCE.getUserDao();
		connectedClients.put(userDto.phoneNumber, connectedClient);
		UserEntity userEntity = MapperImpl.INSTANCE.mapFromUserDto(userDto);
		if(userDao.insertUser(userEntity) != 0){
			try {
				connectedClient.registerationSuccess();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void logout(UserAuthDto userAuthDto) {
		// maybe add additional check to see if he is connected or not
		connectedClients.remove(userAuthDto.phoneNumber);
	}

	@Override
	public void sendMessage(MessageDto messageDto) {

		GroupDao group = new GroupDaoImpl();
		group.getUsersByGroupId(messageDto.groupID);

		// ask db to get all users joined to this group id from messageDto
		/*
		 * for (ConnectedClient connectedClient : connectedClients) {
		 * 
		 * 
		 * if(message.groupID.equals(connectedClient.)){
		 * connectedClient.recieveInvitation(invitationDto.senderPhone);
		 * }
		 * }
		 * 
		 */
	}

	@Override
	public void sendInvitation(InvitationDto invitationDto) {

		// getting from db to check all avaialble numbers in database
		// delegate the calling and bussiness to another class
		invitationDto.invitedPhones.forEach(x -> {
			try {
				connectedClients.get(x).recieveInvitation(invitationDto.senderPhone);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}