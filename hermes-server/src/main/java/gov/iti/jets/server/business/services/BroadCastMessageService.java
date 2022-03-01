package gov.iti.jets.server.business.services;

import common.business.services.Client;

import java.util.Map;

public interface BroadCastMessageService {
    public void sendMessageToAllOnlineUsers(String broadCastMessage, Map<String, Client> connectedClients);
}
