package gov.iti.jets.server.business.services;

import common.business.dtos.InvitationRecievedDto;
import common.business.dtos.InvitationResponseDto;
import common.business.dtos.InvitationSentDto;
import common.business.services.Client;

import java.util.Map;

public interface InvitationService {

	public void sendInvitation(InvitationSentDto invitationSentDto, Map<String, Client> connectedClients);
	public void acceptInvitation(InvitationRecievedDto invitationRecievedDto);
	public void updatingInvitation(InvitationResponseDto invitationResponseDto);
}
