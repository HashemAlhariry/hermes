package gov.iti.jets.server.business.daos;

import java.util.List;
import gov.iti.jets.server.persistance.entities.InvitationEntity;

public interface InvitationDao {
	boolean createInvitation(InvitationEntity invitationEntity);

	void updateInvitationStatus(InvitationEntity invitationEntity);

	void deleteInvitation(InvitationEntity invitationEntity);

	List<InvitationEntity> getPendingInvitationsByReciever(InvitationEntity invitationEntity);
	boolean checkInvitationAvailability(InvitationEntity invitationEntity);
}
