package gov.iti.jets.server.persistance.entities;

import java.sql.Date;

public class MessageEntity {

	public int id;
	public String content;
	public Date sendDate;
	public String senderPhone;
	public int receiverId;

	public MessageEntity() {
	}

	public MessageEntity(int id, String content, Date sendDate, String sender, int receiver) {
		this.id = id;
		this.content = content;
		this.sendDate = sendDate;
		this.senderPhone = sender;
		this.receiverId = receiver;
	}
	
	public MessageEntity(String content, Date sendDate, String sender, int receiver) {
		this.content = content;
		this.sendDate = sendDate;
		this.senderPhone = sender;
		this.receiverId = receiver;
	}
}
