package common.business.services;

import common.business.dtos.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.business.dtos.InvitationResponseDto;

import java.util.List;

// This will be implemented on serverSide
public interface Server extends Remote {

	public void login(Client connectedClient, UserAuthDto userAuthDto) throws RemoteException;

	public void register(Client connectedClient, UserDto userDto) throws RemoteException;

	public void sendMessage(MessageDto message) throws RemoteException;

	public void sendInvitation(InvitationSentDto invitationDto) throws RemoteException;

	public void logout(UserAuthDto userAuthDto) throws RemoteException;

	public List<GroupDto> getAllGroupsByUser(UserDto userDto) throws RemoteException;

	public List<MessageDto> getAllMessagesByGroup(Integer groupId) throws RemoteException;

	public void invitationResponse(InvitationResponseDto invitationResponseDto) throws RemoteException;

	public void addPrivateChat(PrivateGroupDetailsDto privateGroupDetailsDto) throws RemoteException;

	public void addGroupChat(GroupDetailsDto groupDetailsDto) throws RemoteException;

	public void sendBroadCastToOnlineUsers(String broadCastMessage) throws RemoteException;
}
