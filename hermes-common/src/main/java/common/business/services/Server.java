package common.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;

// This will be implemented on serverSide
public interface Server extends Remote {

  public void login(Client connectedClient,UserAuthDto userAuthDto)throws RemoteException ;
  public void register(Client connectedClient)throws RemoteException ;
  public void sendMessage(MessageDto message)throws RemoteException ;
  public void sendInvitation(InvitationSentDto invitationDto)throws RemoteException ;
  public void logout(UserAuthDto userAuthDto)throws RemoteException ;
  public void invitationResponse(InvitationResponse invitationResponse) throws RemoteException;
}
