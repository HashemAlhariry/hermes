package gov.iti.jets.server.business.services;

import common.business.dtos.InvitationRecievedDto;
import common.business.dtos.InvitationResponse;
import common.business.dtos.InvitationSentDto;
import common.business.services.Client;

import java.util.List;
import java.util.Map;

public interface InvitationService {

	public void sendInvitation(InvitationSentDto invitationSentDto, Map<String, Client> connectedClients);
	public void acceptInvitation(InvitationRecievedDto invitationRecievedDto);
	public void updatingInvitation(InvitationResponse invitationResponse);
}
