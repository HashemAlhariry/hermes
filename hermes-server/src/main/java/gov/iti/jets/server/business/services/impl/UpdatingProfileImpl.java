package gov.iti.jets.server.business.services.impl;

import common.business.services.UpdatingProfile;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UpdatingProfileImpl extends UnicastRemoteObject implements UpdatingProfile {

    public UpdatingProfileImpl() throws RemoteException {
        super();
    }
    
}
