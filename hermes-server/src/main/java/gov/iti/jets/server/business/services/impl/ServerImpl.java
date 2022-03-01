package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import common.business.dtos.GroupDto;
import common.business.dtos.GroupStatusDto;
import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Client;
import common.business.services.Server;
import common.business.util.OnlineStatus;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.daos.UserDao;
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
		// userEntity = UserMapperImpl.INSTANCE.mapFromUserAuthDto(userAuthDto);
		Optional<UserEntity> userEntity = userDao.getUserByPhone(phoneNumber);
		System.out.println("loginserver");
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
		System.out.println("loginserver");
		System.out.println("ph : " + userEntity.phone + " p: " + userEntity.password);

		if (userDao.loginUser(userEntity)) {
			try {
				Optional<UserEntity> usOptional = userDao.getUserByPhone(userAuthDto.phoneNumber);
				UserEntity userEntity2 = usOptional.get();
				UserDto userDto = UserMapperImpl.INSTANCE.mapToUserDto(userEntity2);
				connectedClients.put(userAuthDto.phoneNumber, connectedClient);
				connectedClient.loginSuccess(userDto);
				System.out.println(userDto.bio);
				System.out.println(userDto.gender);
				// notify other online contacts that i am online
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
	public void register(Client connectedClient, UserDto userDto) {
		// registered user will be connected?
		// map userDto to userEntity
		// call userDao to insert user data
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		connectedClients.put(userDto.phoneNumber, connectedClient);
		UserEntity userEntity = UserMapperImpl.INSTANCE.mapFromUserDto(userDto);
		if (true) {
			try {
				connectedClient.registerationSuccess();
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
	public void sendInvitation(InvitationSentDto invitationDto) {
		InvitationService invitation = new InvitationServiceImpl();
		invitation.sendInvitation(invitationDto, connectedClients);
	}

	@Override
	public void invitationResponse(InvitationResponse invitationResponse) throws RemoteException {
		InvitationService invitation = new InvitationServiceImpl();
		invitation.updatingInvitation(invitationResponse);
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

	private void setOnlineStatus(OnlineStatus status, String phoneNumber) {
		UserDao userDao = DaosFactory.INSTANCE.getUserDao();
		Optional<UserEntity> usOptional = userDao.getUserByPhone(phoneNumber);
		UserEntity userEntity2 = usOptional.get();
		UserDto userDto = UserMapperImpl.INSTANCE.mapToUserDto(userEntity2);
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

}
