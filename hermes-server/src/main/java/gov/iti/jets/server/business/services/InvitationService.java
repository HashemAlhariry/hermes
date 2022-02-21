package gov.iti.jets.server.business.services;

import common.business.dtos.InvitationRecievedDto;
import common.business.dtos.InvitationSentDto;

public interface InvitationService {

	public void sendInvitation(InvitationSentDto invitationSentDto);
	
	public void acceptInvitation(InvitationRecievedDto invitationRecievedDto);
}
