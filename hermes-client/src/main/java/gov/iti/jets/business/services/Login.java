package gov.iti.jets.business.services;
import java.rmi.Remote;
import java.rmi.RemoteException;

import gov.iti.jets.business.dtos.UserDto;

public interface Login extends Remote {

    public boolean isAuthenticated(UserDto userDto) throws RemoteException;
    
}
