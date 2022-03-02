package gov.iti.jets.server.business.daos;

import java.util.List;
import gov.iti.jets.server.persistance.entities.MessageEntity;

public interface MessageDao {

	List<MessageEntity> getAllMessagesByGroup(Integer groupId);

	void insertMessage(MessageEntity messageEntity);


}
