package common.business.dtos;

import java.util.List;
import java.io.Serializable;

public class InvitationResponse implements Serializable {

    public String senderPhone;
    public String receiverPhone;
    public int response;

    public InvitationResponse(String senderPhone, String receiverPhone, int response) {
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.response = response;
    }



}
