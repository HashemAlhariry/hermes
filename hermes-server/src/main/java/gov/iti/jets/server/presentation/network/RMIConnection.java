package gov.iti.jets.server.presentation.network;

import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import common.business.services.Login;
import gov.iti.jets.server.business.services.impl.LoginImpl;

public enum RMIConnection {
	INSTANCE;

	private Registry registry;

	private Map<String, Remote> services;

	public void init() {
		services = new HashMap<>();
		try {
			registry = LocateRegistry.getRegistry();
			addAllServices();
			loadAllServices();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void addAllServices() throws RemoteException {
		services.put("Login", new LoginImpl());

	}

	private void loadAllServices() throws AccessException, RemoteException {
		for (var entry : services.entrySet()) {
			registry.rebind(entry.getKey(), entry.getValue());
		}
	}

	public void close() throws AccessException, RemoteException, NotBoundException {
		for (var service : registry.list()) {
			registry.unbind(service);
		}
		for (var entry : services.entrySet()) {
			UnicastRemoteObject.unexportObject(entry.getValue(), true);
		}
	}
}
