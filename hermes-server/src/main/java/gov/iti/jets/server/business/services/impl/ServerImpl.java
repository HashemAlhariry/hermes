package gov.iti.jets.server.business.services.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import common.business.dtos.GroupDetailsDto;
import common.business.dtos.GroupDto;
import common.business.dtos.GroupStatusDto;
import common.business.dtos.InvitationResponseDto;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.PrivateGroupDetailsDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Client;
import common.business.services.Server;
import common.business.util.OnlineStatus;
import gov.iti.jets.server.business.daos.GroupUserDao;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.business.services.BroadCastMessageService;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.business.services.InvitationService;
import gov.iti.jets.server.business.services.MessageService;
import gov.iti.jets.server.business.services.PrivateGroupService;
import gov.iti.jets.server.persistance.daos.impl.GroupUserDaoImpl;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;
import gov.iti.jets.server.presentation.gui.util.StatisticsData;
import gov.iti.jets.server.presentation.network.RMIConnection;

public class ServerImpl extends UnicastRemoteObject implements Server {

	// connected Clients will be used for getting online users

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
				GroupService groupService = new GroupServiceImpl();
				var groups = groupService.getAllGroupsByUserWithChatType(userDto);
				for (GroupDto groupDto : groups) {
					if (groupDto.status != OnlineStatus.GROUP) {
						String peerPhone = groupService.getPrivateChatPeerPhone(groupDto.id, userDto);
						if (connectedClients.containsKey(peerPhone)) {
							connectedClients.get(peerPhone)
									.updateContactStatus(
											new GroupStatusDto(groupDto.id, connectedClient.getOnlineStatus()));
						}
					}
				}
				StatisticsData.INSTANCE.setOnlineUsers(StatisticsData.INSTANCE.onlineUsers.get() + 1);
				StatisticsData.INSTANCE.setOfflineUsers(StatisticsData.INSTANCE.offlineUsers.get() - 1);

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

				// updating statistics in server
				StatisticsData.INSTANCE.setOnlineUsers(StatisticsData.INSTANCE.onlineUsers.get() + 1);

				// updating statistics in server
				if (userDto.gender)
					StatisticsData.INSTANCE.setMaleUsers(StatisticsData.INSTANCE.maleUsers.get() + 1);
				else
					StatisticsData.INSTANCE.setFemaleUsers(StatisticsData.INSTANCE.femaleUsers.get() + 1);

				// updating statistics in server
				StatisticsData.INSTANCE.updatePieChartDataForCountry(userDto.country);
				StatisticsData.INSTANCE.setAllUsers(StatisticsData.INSTANCE.allUsers.get() + 1);

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
		setOnlineStatus(OnlineStatus.OFFLINE, phoneNumber);
		// maybe add additional check to see if he is connected or not
		connectedClients.remove(phoneNumber);
		System.out.println("User: " + phoneNumber + " logged out");
		// connectedClients.remove(userAuthDto.phoneNumber);

		StatisticsData.INSTANCE.setOnlineUsers(StatisticsData.INSTANCE.onlineUsers.get() - 1);
		StatisticsData.INSTANCE.setOfflineUsers(StatisticsData.INSTANCE.offlineUsers.get() + 1);

	}

	@Override
	public void sendMessage(MessageDto messageDto) {

		MessageService messageService = new MessageServiceImpl();
		messageService.insertMessage(messageDto);

		// send message to all clients connected to it and being online
		GroupUserDao groupUserDao = new GroupUserDaoImpl();

		List<String> userPhones = groupUserDao.getAllUsersByGroup(messageDto.groupId);

		userPhones.remove(messageDto.senderPhone);

		for (String userPhone : userPhones) {
			// check if group participants online or not
			if (connectedClients.containsKey(userPhone)) {
				try {
					connectedClients.get(userPhone).recieveMessage(messageDto);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}

		// call message recieve in client to check if the message will append to the
		// currenct v box or not

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
	public List<GroupDto> getAllGroupsByUser(UserDto userDto) {
		GroupService groupService = new GroupServiceImpl();
		var groups = groupService.getAllGroupsByUserWithChatType(userDto);
		for (GroupDto group : groups) {
			if (group.status != OnlineStatus.GROUP) {
				String peerPhone = groupService.getPrivateChatPeerPhone(group.id, userDto);
				if (connectedClients.containsKey(peerPhone)) {
					try {
						group.status = connectedClients.get(peerPhone).getOnlineStatus();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else {
					group.status = OnlineStatus.OFFLINE;
				}
			}
		}
		return groups;
	}

	@Override
	public List<MessageDto> getAllMessagesByGroup(Integer groupId) {
		MessageService messageService = new MessageServiceImpl();
		return messageService.getAllMessagesByGroup(groupId);
	}

	@Override
	public byte[] getUserImageByPhone(String phone) throws RemoteException {
		// TODO: Create UserService that have method takes phone and returns the path
		// userImages/picname.png
		// TODO: Then we pass the path another method in UserService that returns the
		// image bytes
		return null;
	}

	@Override
	public void changeMyOnlineStatus(OnlineStatus status, String phoneNumber) {
		setOnlineStatus(status, phoneNumber);
	}

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
		broadCastMessageService.sendMessageToAllOnlineUsers(broadCastMessage, connectedClients);
	}

	private void setOnlineStatus(OnlineStatus status, String phoneNumber) {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		Optional<UserEntity> userEntetityOptional = userDao.getUserByPhone(phoneNumber);
		UserEntity userEntity = userEntetityOptional.get();
		UserDto userDto = UserMapperImpl.INSTANCE.mapToUserDto(userEntity);
		GroupService groupService = new GroupServiceImpl();
		var groups = groupService.getAllGroupsByUserWithChatType(userDto);
		for (GroupDto groupDto : groups) {
			if (groupDto.status != OnlineStatus.GROUP) {
				String peerPhone = groupService.getPrivateChatPeerPhone(groupDto.id, userDto);
				if (connectedClients.containsKey(peerPhone)) {
					try {
						connectedClients.get(peerPhone)
								.updateContactStatus(new GroupStatusDto(groupDto.id, status));
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void sendServerAvailability(boolean serverAvailability) {
		for (Map.Entry<String, Client> client : connectedClients.entrySet()) {
			try {
				client.getValue().serverAvailability(serverAvailability);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public byte[] getUserProfilePicture(String phone) throws RemoteException {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		String image = userDao.getUserImageByPhone(phone);
		if (image == null)
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

	@Override
	public boolean getServerAvailability() throws RemoteException {
		return RMIConnection.INSTANCE.serverAvailability.get();
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
}
