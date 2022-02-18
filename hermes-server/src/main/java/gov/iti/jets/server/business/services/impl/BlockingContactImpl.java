package gov.iti.jets.server.business.services.impl;

import common.business.services.BlockingContact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BlockingContactImpl extends UnicastRemoteObject implements BlockingContact {

    public BlockingContactImpl() throws RemoteException {
        super();
   
    }
    
}
