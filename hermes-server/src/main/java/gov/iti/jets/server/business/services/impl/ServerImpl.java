package gov.iti.jets.server.business.services.impl;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import common.business.dtos.InvitationResponseDto;
import common.business.dtos.GroupDto;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Client;

import common.business.services.Server;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.daos.UserDao;
import common.business.dtos.*;
import common.business.services.Client;
import common.business.services.Server;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.services.*;
import gov.iti.jets.server.persistance.daos.impl.GroupDaoImpl;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;
import gov.iti.jets.server.presentation.gui.util.StatisticsData;

import java.util.List;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.business.services.InvitationService;
import gov.iti.jets.server.business.services.MessageService;
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
	public UserDto checkPhone(Client connectedClient, String phoneNumber) {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		Optional<UserEntity> userEntity = userDao.getUserByPhone(phoneNumber);
		if (userEntity.isPresent()) {
			try {
				UserDto userDto = UserMapperImpl.INSTANCE.mapToUserDto(userEntity.get());
				connectedClient.loginSuccess(userDto);
				return userDto;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("NOOO Access: " + phoneNumber);
		}
		return null;

	}

	@Override
	public UserDto login(Client connectedClient, UserAuthDto userAuthDto) {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		System.out.println("userDto password: " + userAuthDto.password);
		UserEntity userEntity = UserMapperImpl.INSTANCE.mapFromUserAuthDto(userAuthDto);

		if (userDao.loginUser(userEntity)) {
			try {
				Optional<UserEntity> usOptional = userDao.getUserByPhone(userAuthDto.phoneNumber);
				UserEntity userEntity2 = usOptional.get();
				UserDto userDto = UserMapperImpl.INSTANCE.mapToUserDto(userEntity2);
				connectedClients.put(userAuthDto.phoneNumber, connectedClient);
				connectedClient.loginSuccess(userDto);

				StatisticsData.INSTANCE.setOnlineUsers(StatisticsData.INSTANCE.onlineUsers.get()+1);
				StatisticsData.INSTANCE.setOfflineUsers(StatisticsData.INSTANCE.offlineUsers.get()-1);

				return userDto;

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("NOOO");
		}
		return null;

	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		UserEntity userEntity2 = UserMapperImpl.INSTANCE.mapFromUserDto(userDto);
		userDao.updateUser(userEntity2);
		userDto = UserMapperImpl.INSTANCE.mapToUserDto(userEntity2);
		return userDto;
	}

	@Override
	public void register(Client connectedClient, UserDto userDto) {
		// registered user will be connected
		// map userDto to userEntity
		// call userDao to insert user data
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		connectedClients.put(userDto.phoneNumber, connectedClient);
		UserEntity userEntity = UserMapperImpl.INSTANCE.mapFromUserDto(userDto);

		String insertionResponse = userDao.insertUser(userEntity);
		if (insertionResponse == null) {
			try {
				connectedClient.registerationSuccess();

		      //updating statistics in server
			  StatisticsData.INSTANCE.setOnlineUsers(StatisticsData.INSTANCE.onlineUsers.get()+1);
			  StatisticsData.INSTANCE.setOfflineUsers(StatisticsData.INSTANCE.offlineUsers.get()-1);

				if(userDto.gender)
					StatisticsData.INSTANCE.setMaleUsers(StatisticsData.INSTANCE.maleUsers.get()+1);
				else
					StatisticsData.INSTANCE.setFemaleUsers(StatisticsData.INSTANCE.femaleUsers.get()+1);

				StatisticsData.INSTANCE.updatePieChartDataForCountry(userDto.country);



			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			try {
				connectedClient.registerationFail(insertionResponse);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void logout(String phoneNumber) {
		//setOnlineStatus(OnlineStatus.OFFLINE, phoneNumber);
		// maybe add additional check to see if he is connected or not
		connectedClients.remove(phoneNumber);
		System.out.println("User: " + phoneNumber + " logged out");
		// connectedClients.remove(userAuthDto.phoneNumber);

		StatisticsData.INSTANCE.setOnlineUsers(StatisticsData.INSTANCE.onlineUsers.get() - 1);
		StatisticsData.INSTANCE.setOfflineUsers(StatisticsData.INSTANCE.offlineUsers.get() + 1);

	}

	@Override
	public void sendMessage(MessageDto messageDto) {
		GroupDao group = new GroupDaoImpl();
		group.getUsersByGroupId(messageDto.groupID);
	}

	@Override
	public void invitationResponse(InvitationResponseDto invitationResponseDto) throws RemoteException {
		InvitationService invitation = new InvitationServiceImpl();
		invitation.updatingInvitation(invitationResponseDto);
	}

	@Override
	public void sendInvitation(InvitationSentDto invitationDto) {
		InvitationService invitation = new InvitationServiceImpl();
		invitation.sendInvitation(invitationDto, connectedClients);
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
	public void addPrivateChat(PrivateGroupDetailsDto privateGroupDetailsDto) throws RemoteException {
		PrivateGroupService privateGroupService = new PrivateGroupServiceImpl();
		privateGroupService.addNewPrivateGroupChat(privateGroupDetailsDto);
	}

	@Override
	public void addGroupChat(GroupDetailsDto groupDetailsDto) throws RemoteException {
		GroupService groupService = new GroupServiceImpl();
		groupService.addNewGroupChat(groupDetailsDto);
	}

	@Override
	public void sendBroadCastToOnlineUsers(String broadCastMessage) throws RemoteException {

		BroadCastMessageService broadCastMessageService = new BroadCastMessageServiceImpl();
		broadCastMessageService.sendMessageToAllOnlineUsers(broadCastMessage,connectedClients);
	}	
	
	public boolean setUserProfilePicture(byte[] bytes, UserDto userDto, String format) throws RemoteException {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		try (var img = new FileOutputStream("src/main/resources/userImages/" + userDto.phoneNumber + "." + format)) {
			img.write(bytes);
			userDao.setUserProfilePicture(userDto.phoneNumber, format);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public byte[] getUserProfilePicture(String phone) throws RemoteException {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		String image = userDao.getUserImageByPhone(phone);
		if (image==null)
			return null;
		System.out.println("image in server :" + image);
		try (var img = new FileInputStream("src/main/resources/userImages/" + image)) {

			byte[] b = img.readAllBytes();
			System.out.println("bytes in server :" + b);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
}
}
