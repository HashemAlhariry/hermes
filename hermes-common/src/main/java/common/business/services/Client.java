package common.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;

public interface Client extends Remote {

	public void recieveMessage(MessageDto message) throws RemoteException;

	public void recieveInvitation(String sender) throws RemoteException;

	public String getPhoneNumber() throws RemoteException;

	public void loginSuccess(UserDto userDto) throws RemoteException;

	public void registerationSuccess() throws RemoteException;


}
