package common.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import common.business.dtos.GroupDto;
import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;

// This will be implemented on serverSide
public interface Server extends Remote {

	public UserDto login(Client connectedClient, UserAuthDto userAuthDto) throws RemoteException;

	public UserDto updateUser(UserDto userDto) throws RemoteException;

	public UserDto checkPhone(Client connectedClient, String phoneNumber)  throws RemoteException;
	
	public void register(Client connectedClient, UserDto userDto) throws RemoteException;

	public void sendMessage(MessageDto message) throws RemoteException;

	public void sendInvitation(InvitationSentDto invitationDto) throws RemoteException;

	public void logout(String phoneNumber) throws RemoteException;

	public List<GroupDto> getAllGroupsByUser(UserDto userDto) throws RemoteException;

	public List<MessageDto> getAllMessagesByGroup(Integer groupId) throws RemoteException;

	public void invitationResponse(InvitationResponse invitationResponse) throws RemoteException;

	public boolean setUserProfilePicture(byte[]bytes,UserDto userDto ,String format) throws RemoteException;

	public byte[] getUserProfilePicture(String phone) throws RemoteException;
}

