package gov.iti.jets.server.business.services.impl;

import common.business.services.AddingContact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddingContactImpl extends UnicastRemoteObject implements AddingContact {

    public AddingContactImpl() throws RemoteException {
        super();
    }
    
}
