package common.business.services;
import java.rmi.Remote;
import java.rmi.RemoteException;

import common.business.dtos.UserDto;

public interface Login extends Remote {
    
    public boolean isAuthenticated(UserDto userDto) throws RemoteException;
}
