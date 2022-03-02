package gov.iti.jets.server.persistance.entities;

import java.sql.Date;

public class MessageEntity {

	public int id;
	public String content;
	public Date sendDate;
	public int groupId;
	public String senderPhone;

	public MessageEntity() {
	}

	public MessageEntity(
			int id,
			String content,
			Date sendDate,
			int receiver,
			String sender) {
		this.id = id;
		this.content = content;
		this.sendDate = sendDate;
		this.groupId = receiver;
		this.senderPhone = sender;
	}

	public MessageEntity(
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
