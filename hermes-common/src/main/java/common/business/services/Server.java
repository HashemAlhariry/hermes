package common.business.services;

import common.business.dtos.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.util.OnlineStatus;
import common.business.dtos.InvitationResponseDto;

import java.util.List;

// This will be implemented on serverSide
public interface Server extends Remote {

	public UserDto login(Client connectedClient, UserAuthDto userAuthDto) throws RemoteException;

	public UserDto checkPhone(Client connectedClient, String phoneNumber) throws RemoteException;

	public void register(Client connectedClient, UserDto userDto) throws RemoteException;

	public void sendMessage(MessageDto message) throws RemoteException;

	public void sendInvitation(InvitationSentDto invitationDto) throws RemoteException;

	public void logout(String phoneNumber) throws RemoteException;

	public List<GroupDto> getAllGroupsByUser(UserDto userDto) throws RemoteException;

	public List<MessageDto> getAllMessagesByGroup(Integer groupId) throws RemoteException;

	public byte[] getUserImageByPhone(String phone) throws RemoteException;

	public void changeMyOnlineStatus(OnlineStatus status, String phoneNumber) throws RemoteException;

	public void invitationResponse(InvitationResponseDto invitationResponseDto) throws RemoteException;

	public void addPrivateChat(PrivateGroupDetailsDto privateGroupDetailsDto) throws RemoteException;

	public void addGroupChat(GroupDetailsDto groupDetailsDto) throws RemoteException;

	public void sendBroadCastToOnlineUsers(String broadCastMessage) throws RemoteException;

	public void sendServerAvailability(boolean serverAvailability) throws  RemoteException;

	public boolean getServerAvailability() throws RemoteException;


}
