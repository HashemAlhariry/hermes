package common.business.dtos;

public class MessageDto {
   public String senderPhone;
   public String content;
   public Long groupID;
   
public MessageDto(String senderPhone, String content, Long groupID) {
    this.senderPhone = senderPhone;
    this.content = content;
    this.groupID = groupID;
}
}
