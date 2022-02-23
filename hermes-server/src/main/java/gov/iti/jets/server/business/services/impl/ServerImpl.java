package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.services.Client;
import common.business.services.Server;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.services.InvitationService;
import gov.iti.jets.server.persistance.daos.impl.GroupDaoImpl;

public class ServerImpl extends UnicastRemoteObject implements Server {

    // connected Clients will be used for getting online users
    private Map<String, Client> connectedClients;

    public ServerImpl() throws RemoteException {
        super();
        connectedClients = new HashMap<>();
    }

    @Override
    public void login(Client connectedClient, UserAuthDto userAuthDto) {

        // call another class for authenticating db
        // checking if user exists or not
        
        connectedClients.put(userAuthDto.phoneNumber, connectedClient);
        System.out.println("User phone added to online users " + userAuthDto.phoneNumber);
        try {
            connectedClient.loginSuccess();
        } catch (RemoteException e) {
     
            e.printStackTrace();
        }
    }

    @Override
    public void register(Client connectedClient) {
    }

    @Override
    public void logout(UserAuthDto userAuthDto) {
        // maybe add additional check to see if he is connected or not
        connectedClients.remove(userAuthDto.phoneNumber);
    }

    @Override
    public void invitationResponse(InvitationResponse invitationResponse) throws RemoteException {
        InvitationService invitation = new InvitationServiceImpl();
        invitation.updatingInvitation(invitationResponse);
    }

    @Override
    public void sendMessage(MessageDto messageDto) {

        GroupDao group = new GroupDaoImpl();
        group.getUsersByGroupId(messageDto.groupID);


    }

    @Override
    public void sendInvitation(InvitationSentDto invitationDto) {
       InvitationService invitation = new InvitationServiceImpl();
       invitation.sendInvitation(invitationDto,connectedClients);
    }

}
