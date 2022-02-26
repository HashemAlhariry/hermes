package gov.iti.jets.server.business.services.impl;

import common.business.services.Client;
import gov.iti.jets.server.business.services.BroadCastMessageService;

import java.rmi.RemoteException;
import java.util.Map;

public class BroadCastMessageServiceImpl implements BroadCastMessageService {

    @Override
    public void sendMessageToAllOnlineUsers(String broadCastMessage, Map<String, Client> connectedClients) {
        for (Map.Entry<String, Client> client : connectedClients.entrySet()) {
            try {
                client.getValue().receiveBroadCastMessage(broadCastMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
}
