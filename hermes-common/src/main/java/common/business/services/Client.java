package common.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.business.dtos.GroupStatusDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserDto;
import common.business.util.OnlineStatus;

public interface Client extends Remote {

	public void recieveMessage(MessageDto message) throws RemoteException;

	public void recieveInvitation(String sender) throws RemoteException;

	public String getPhoneNumber() throws RemoteException;

	public void loginSuccess(UserDto userDto) throws RemoteException;

	public void registerationSuccess() throws RemoteException;

	public void updateContactStatus(GroupStatusDto groupStatusDto) throws RemoteException;

	public OnlineStatus getOnlineStatus() throws RemoteException;

	public void registerationFail(String errorMessage) throws RemoteException;

	public void receiveBroadCastMessage(String broadCastMessage) throws RemoteException;

	public void serverAvailability(boolean checkServerAvailability) throws RemoteException;

}
