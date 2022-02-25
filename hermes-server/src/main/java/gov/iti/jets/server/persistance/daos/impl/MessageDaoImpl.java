package gov.iti.jets.server.persistance.daos.impl;

import java.util.List;
import gov.iti.jets.server.business.daos.MessageDao;
import gov.iti.jets.server.persistance.entities.MessageEntity;

/**
 * MessageDaoImpl
 */
public class MessageDaoImpl implements MessageDao {

	@Override
	public List<MessageEntity> getAllMessagesByGroup(Integer groupId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void insertMessage(MessageEntity messageEntity) {
		// TODO Auto-generated method stub

	}

}
