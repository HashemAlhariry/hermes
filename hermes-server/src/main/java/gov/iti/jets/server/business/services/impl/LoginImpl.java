package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.business.dtos.UserDto;
import common.business.services.Login;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.daos.impl.UserDaoImpl;

public class LoginImpl extends UnicastRemoteObject implements Login {

    private UserDao userDao;

    public LoginImpl() throws RemoteException {
        super();
		userDao = new UserDaoImpl();
    }



    @Override
    public boolean isAuthenticated(UserDto userDto) throws RemoteException {
		System.out.println("service was called from client");
        return userDao.getUserRegistered(userDto) != null;
    }

}
