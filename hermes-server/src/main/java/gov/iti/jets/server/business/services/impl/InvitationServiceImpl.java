package gov.iti.jets.server.business.services.impl;

import common.business.dtos.InvitationRecievedDto;
import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
import common.business.services.Client;
import gov.iti.jets.server.business.services.InvitationService;
import gov.iti.jets.server.persistance.entities.InvitationEntity;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.entities.enums.InvitationStatus;
import gov.iti.jets.server.persistance.util.DaosFactory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InvitationServiceImpl implements InvitationService{


	@Override
	public void sendInvitation(InvitationSentDto invitationSentDto, Map<String, Client> connectedClients) {

        System.out.print(invitationSentDto.senderPhone +" ALL PHONES INVITED ARE ");
        invitationSentDto.invitedPhones.forEach(phone -> {
            System.out.print(phone+" ");
        });
        System.out.println();

		String sender = invitationSentDto.senderPhone;
        invitationSentDto.invitedPhones.forEach(clientPhone -> {

            try {
                // caseChecked to make the invitation send to the receiver or not
                var casesChecked=false;

                // 1- receiver is in the database 
                Optional<UserEntity> client = DaosFactory.INSTANCE.getUserDao().getUserByPhone(clientPhone);

                // 2- check that if receiver and sender having common group
                if(client.isPresent()){
					casesChecked = DaosFactory.INSTANCE.getGroupDao().checkPrivateChatEstablished(sender,clientPhone);
					System.out.println("CASES CHECKED =" + casesChecked);
				}

                //check all testcasses passed and check if already invitation send for a private chat before
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
    public void updatingInvitation(InvitationResponse invitationResponse) {
        InvitationStatus invitationStatus;
        if(invitationResponse.response==1)
         invitationStatus=InvitationStatus.ACCEPTED;
        else
          invitationStatus=InvitationStatus.REJECTED;
        DaosFactory.INSTANCE.getInvitationDao().updateInvitationStatus(new InvitationEntity(0,invitationResponse.senderPhone,invitationResponse.receiverPhone,invitationStatus));

    }

}
