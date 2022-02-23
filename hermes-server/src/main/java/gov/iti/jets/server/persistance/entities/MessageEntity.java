package gov.iti.jets.server.persistance.entities;

import java.sql.Date;

public class MessageEntity {

	public int id;
	public String content;
	public Date sendDate;

	public MessageEntity() {
	}

	public MessageEntity(int id, String content, Date sendDate) {
		this.id = id;
		this.content = content;
		this.sendDate = sendDate;
	}
}
