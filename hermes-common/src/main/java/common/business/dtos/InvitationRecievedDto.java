package common.business.dtos;

import java.io.Serializable;

public class InvitationRecievedDto implements Serializable {

    public String senderPhone;
    public String recieverPhone;

    public InvitationRecievedDto(String senderPhone, String recieverPhone) {
		this.senderPhone = senderPhone;
		this.recieverPhone = recieverPhone;
	}


}
