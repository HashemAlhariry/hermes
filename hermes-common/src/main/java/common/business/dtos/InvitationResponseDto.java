package common.business.dtos;

import java.io.Serializable;

public class InvitationResponseDto implements Serializable {

    public String senderPhone;
    public String receiverPhone;
    public int response;

    public InvitationResponseDto(String senderPhone, String receiverPhone, int response) {
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.response = response;
    }



}
