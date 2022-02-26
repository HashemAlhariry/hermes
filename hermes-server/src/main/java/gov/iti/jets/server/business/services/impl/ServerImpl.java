package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import common.business.dtos.*;
import common.business.services.Client;
import common.business.services.Server;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.business.services.InvitationService;
import gov.iti.jets.server.business.services.PrivateGroupService;
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
    public void invitationResponse(InvitationResponseDto invitationResponseDto) throws RemoteException {
        InvitationService invitation = new InvitationServiceImpl();
        invitation.updatingInvitation(invitationResponseDto);
    }


    @Override
    public void addPrivateChat(PrivateGroupDetailsDto privateGroupDetailsDto) throws RemoteException {
        PrivateGroupService privateGroupService = new PrivateGroupServiceImpl();
        privateGroupService.addNewPrivateGroupChat(privateGroupDetailsDto);
    }

    @Override
    public void addGroupChat(GroupDetailsDto groupDetailsDto) throws RemoteException {
        GroupService groupService = new GroupServiceImpl();
        groupService.addNewGroupChat(groupDetailsDto);
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
