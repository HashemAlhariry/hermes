package gov.iti.jets.client.presistance.network;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
 
import common.business.services.Server;

public enum RMIConnection {

    INSTANCE;
    private Map<String, Remote> services;
    private Registry registry;

    public void init() {
        services = new HashMap<>();
        try {
            registry = LocateRegistry.getRegistry();
            getAllServices();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    void getAllServices() throws AccessException, RemoteException, NotBoundException {
        for (String service : registry.list()) {
            services.put(service, registry.lookup(service));
        }
    }

    public Server getServer() {
        return (Server) services.get("Server");
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
