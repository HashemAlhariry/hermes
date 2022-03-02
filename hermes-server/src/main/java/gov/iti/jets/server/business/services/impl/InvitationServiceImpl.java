package gov.iti.jets.server.business.services.impl;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Optional;

import common.business.dtos.InvitationRecievedDto;
import common.business.dtos.InvitationResponseDto;
import common.business.dtos.InvitationSentDto;
import common.business.services.Client;
import gov.iti.jets.server.business.services.InvitationService;
import gov.iti.jets.server.persistance.entities.InvitationEntity;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.entities.enums.InvitationStatus;
import gov.iti.jets.server.persistance.util.DaosFactory;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Optional;

public class InvitationServiceImpl implements InvitationService{


	@Override
	public void sendInvitation(InvitationSentDto invitationSentDto, Map<String, Client> connectedClients) {

		String sender = invitationSentDto.senderPhone;
        invitationSentDto.invitedPhones.forEach(clientPhone -> {

            try {

                // caseChecked to make the invitation send to the receiver or not
                var casesChecked=false;
                // 1- receiver is in the database 
                Optional<UserEntity> client = DaosFactory.INSTANCE.getUserDao().getUserByPhone(clientPhone);
                if(client.isPresent()){
                    // 2- check that if receiver and sender having common group
					casesChecked = DaosFactory.INSTANCE.getGroupDao().checkPrivateChatEstablished(sender,clientPhone);
					System.out.println("CASES CHECKED =" + casesChecked);
				}
                //check all testcases passed and check if already invitation send for a private chat before
                if(casesChecked && DaosFactory.INSTANCE.getInvitationDao().checkInvitationAvailability(new InvitationEntity(0,invitationSentDto.senderPhone,clientPhone, InvitationStatus.PENDING))){

                    //SEND TO THE ONLINE USER
                    if(connectedClients.containsKey(clientPhone) ){
                        connectedClients.get(clientPhone).recieveInvitation(invitationSentDto.senderPhone);
                    }
                    // save invitation to db when user leave it the notification menu and make it pending
                    DaosFactory.INSTANCE.getInvitationDao().createInvitation(new InvitationEntity(0,invitationSentDto.senderPhone,clientPhone, InvitationStatus.PENDING));
                }

            } catch (RemoteException e) {

                e.printStackTrace();
            }
        });
		
	}





	@Override
	public void acceptInvitation(InvitationRecievedDto invitationRecievedDto) {

	}

    @Override
    public void updatingInvitation(InvitationResponseDto invitationResponseDto) {
        InvitationStatus invitationStatus;
        if(invitationResponseDto.response==1)
         invitationStatus=InvitationStatus.ACCEPTED;
        //PRIVATE CHAT WILL BE ADDED (add to the group table)
        else
          invitationStatus=InvitationStatus.REJECTED;
        DaosFactory.INSTANCE.getInvitationDao().updateInvitationStatus(new InvitationEntity(0, invitationResponseDto.senderPhone, invitationResponseDto.receiverPhone,invitationStatus));

    }

}
