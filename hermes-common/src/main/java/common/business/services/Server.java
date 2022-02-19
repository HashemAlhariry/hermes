package common.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.business.dtos.InvitationDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;

// This will be implemented on serverSide
public interface Server extends Remote {

  public void login(Client connectedClient,UserAuthDto userAuthDto)throws RemoteException ;
  public void register(Client connectedClient)throws RemoteException ;
  public void sendMessage(MessageDto message)throws RemoteException ;
  public void sendInvitation(InvitationDto invitationDto)throws RemoteException ;
  public void logout(UserAuthDto userAuthDto)throws RemoteException ;
  
}
