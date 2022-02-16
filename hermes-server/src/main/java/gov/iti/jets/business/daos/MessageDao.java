package gov.iti.jets.business.daos;

import java.util.List;

import gov.iti.jets.persistance.entities.MessageEntity;

public interface MessageDao {
	
	List<MessageEntity> getAllMessagesByGroup(GroupDao groupDao);
	void insertMessage(MessageEntity messageEntity);
	
}
