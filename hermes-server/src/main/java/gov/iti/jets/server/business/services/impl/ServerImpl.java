package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.dtos.UserAuthDto;
import common.business.services.Client;
import common.business.services.Server;
import gov.iti.jets.server.business.daos.GroupDao;
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

        // call another class for authenicating db
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
    public void sendMessage(MessageDto messageDto) {

        GroupDao group = new GroupDaoImpl();
        group.getUsersByGroupId(messageDto.groupID);

        // ask db to get all users joined to this group id from messageDto
        /*
         * for (ConnectedClient connectedClient : connectedClients) {
         * 
         * 
         * if(message.groupID.equals(connectedClient.)){
         * connectedClient.recieveInvitation(invitationDto.senderPhone);
         * }
         * }
         * 
         */

    }

    @Override
    public void sendInvitation(InvitationSentDto invitationDto) {

        // getting from db to check all avaialble numbers in database
        // delegate the calling and bussiness to another class


        System.out.print(invitationDto.senderPhone +" ALL PHONES INVITED ARE ");
        invitationDto.invitedPhones.forEach(phone -> {
            System.out.print(phone+" ");
        });
        System.out.println();


        invitationDto.invitedPhones.forEach(clientPhone -> {
            try {
                
                //SEND TO THE ONLINE USER
               if(connectedClients.containsKey(clientPhone)){
                     connectedClients.get(clientPhone).recieveInvitation(invitationDto.senderPhone);

                    }else{
                
                //SEND TO DB TO GET TO THE OFFLINE USER WHEN GETTING ONLINE
                System.out.println(clientPhone + " is not Online");
                
               }
            } catch (RemoteException e) {

                e.printStackTrace();
            }
        });


    }

}
