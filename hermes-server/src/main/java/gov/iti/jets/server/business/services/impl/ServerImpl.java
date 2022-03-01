package gov.iti.jets.server.business.services.impl;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import common.business.dtos.GroupDto;
import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Client;

import common.business.services.Server;
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
		connectedClients.remove(phoneNumber);
		System.out.println("User: " + phoneNumber + " logged out");
	}

	@Override
	public void sendMessage(MessageDto messageDto) {
		GroupDao group = new GroupDaoImpl();
		group.getUsersByGroupId(messageDto.groupID);
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
