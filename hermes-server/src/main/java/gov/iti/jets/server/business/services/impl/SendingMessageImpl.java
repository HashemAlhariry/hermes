package gov.iti.jets.server.business.services.impl;

import common.business.services.SendingMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SendingMessageImpl extends UnicastRemoteObject implements SendingMessage {

    public SendingMessageImpl() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }
    
}
