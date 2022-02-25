package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import common.business.dtos.InvitationDto;
import common.business.dtos.GroupDto;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
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
import gov.iti.jets.server.presentation.gui.util.StageCoordinator;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.business.services.InvitationService;
import gov.iti.jets.server.persistance.daos.impl.GroupDaoImpl;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;
import gov.iti.jets.server.business.services.MessageService;
import java.util.List;
public class ServerImpl extends UnicastRemoteObject implements Server {

	private Map<String, Client> connectedClients;

	public ServerImpl() throws RemoteException {
		super();
		connectedClients = new HashMap<>();
	}

	@Override
	public void login(Client connectedClient, UserAuthDto userAuthDto) {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		UserEntity userEntity = MapperImpl.INSTANCE.mapFromUserAuthDto(userAuthDto);
		System.out.println("loginserver");
		System.out.println(userEntity.phone+" " +userEntity.password );
		if(userDao.loginUser(userEntity)){
			try{
				//userDao.getUserByPhone(userAuthDto.phoneNumber);
				
				//user entity to dto
				System.out.println("YYYYY");
				//UserDto userDto = MapperImpl.INSTANCE.mapToUserDto(userEntity);
				connectedClient.loginSuccess();
				connectedClients.put(userAuthDto.phoneNumber, connectedClient);
				System.out.println("YESSSSSS");
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
		}
		else{
			System.out.println("NOOO");
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

	@Override
    public void invitationResponse(InvitationResponse invitationResponse) throws RemoteException {
        InvitationService invitation = new InvitationServiceImpl();
        invitation.updatingInvitation(invitationResponse);
    }


    @Override
    public void sendInvitation(InvitationSentDto invitationDto) {
       InvitationService invitation = new InvitationServiceImpl();
       invitation.sendInvitation(invitationDto,connectedClients);
    }

	@Override
	public List<GroupDto> getAllGroupsByUser(UserDto userDto) throws RemoteException {
		GroupService groupService = new GroupServiceImpl();
		return groupService.getAllGroupsByUser(userDto);
	}


	@Override
	public List<MessageDto> getAllMessagesByGroup(Integer groupId) {
		MessageService messageService = new MessageServiceImpl();
		return messageService.getAllMessagesByGroup(groupId);
	}

	@Override
	public void register(Client connectedClient) throws RemoteException {

	}
}
