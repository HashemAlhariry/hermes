package gov.iti.jets.server.business.services.impl;

import common.business.services.AddingNewContact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddingNewContactImpl extends UnicastRemoteObject implements AddingNewContact {

    public AddingNewContactImpl() throws RemoteException {
        super();
    }
    
}
