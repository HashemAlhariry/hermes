package common.business.dtos;

import java.io.Serializable;
import java.sql.Date;

public class MessageDto implements Serializable {

    public int id;
    public String content;
    public Date sendDate;
    public int receiverId;
    public String senderPhone;

    public MessageDto() {
    }

    public MessageDto(
            int id,
            String content,
            Date sendDate,
            int receiver,
            String sender) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.senderPhone = sender;
        this.receiverId = receiver;
    }

    public MessageDto(
            String content,
            Date sendDate,
            int receiver,
            String sender) {
        this.content = content;
        this.sendDate = sendDate;
        this.receiverId = receiver;
        this.senderPhone = sender;
    }

}
