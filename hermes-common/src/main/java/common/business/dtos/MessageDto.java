package common.business.dtos;

import java.io.Serializable;
import java.sql.Date;

public class MessageDto implements Serializable {

    public int id;
    public String content;
    public Date sendDate;
    public int groupId;
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
        this.groupId = receiver;
    }

    public MessageDto(
            String content,
            Date sendDate,
            int receiver,
            String sender) {
        this.content = content;
        this.sendDate = sendDate;
        this.groupId = receiver;
        this.senderPhone = sender;
    }

}
