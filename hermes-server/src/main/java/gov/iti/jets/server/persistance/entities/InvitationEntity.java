package gov.iti.jets.server.persistance.entities;

import gov.iti.jets.server.persistance.entities.enums.InvitationStatus;

public class InvitationEntity {
	private long id;
	private String senderPhone;
	private String recieverPhone;
	private InvitationStatus status;

	public InvitationEntity(long id, String senderPhone, String recieverPhone, InvitationStatus status) {
		this.id = id;
		this.senderPhone = senderPhone;
		this.recieverPhone = recieverPhone;
		this.status = status;
	}

	public InvitationEntity() {

	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getRecieverPhone() {
		return recieverPhone;
	}

	public void setRecieverPhone(String recieverPhone) {
		this.recieverPhone = recieverPhone;
	}

	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}
}
