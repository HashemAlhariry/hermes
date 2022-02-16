package gov.iti.jets.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gov.iti.jets.business.daos.UserDao;
import gov.iti.jets.business.dtos.UserDto;
import gov.iti.jets.business.services.Login;

public class LoginImpl extends UnicastRemoteObject implements Login  {

    private UserDao userDao;
    
    protected LoginImpl() throws RemoteException {
        super();
    }

    public LoginImpl (UserDao userDao) throws RemoteException {
        this.userDao = userDao;
    }

    @Override
    public boolean isAuthenticated(UserDto userDto) throws RemoteException {
        return  userDao.getUserRegistered(userDto) != null;
    }


    
    
}
