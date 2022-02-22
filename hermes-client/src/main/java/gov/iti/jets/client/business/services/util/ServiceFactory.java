package gov.iti.jets.client.business.services.util;

import java.rmi.RemoteException;

import gov.iti.jets.client.business.services.impl.ClientImpl;

public enum ServiceFactory {
	INSTANCE;

	private ClientImpl clientImplInstance;

	public ClientImpl getClientImpl() throws RemoteException {
		if (clientImplInstance == null)
			clientImplInstance = new ClientImpl();
		return clientImplInstance;
	}

	public void releaseClientImpl(){
		clientImplInstance = null;
	}
}
