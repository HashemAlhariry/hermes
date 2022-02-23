package common.business.dtos;

import java.io.Serializable;
import java.util.List;

public class InvitationDto implements Serializable {

	public String senderPhone;
	public List<String> invitedPhones;

	public InvitationDto(String senderPhone, List<String> invitedPhones) {
		this.senderPhone = senderPhone;
		this.invitedPhones = invitedPhones;
	}

}
