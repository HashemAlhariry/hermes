package gov.iti.jets.server.business.services.impl;

import common.business.dtos.MessageDto;
import gov.iti.jets.server.business.daos.MessageDao;
import gov.iti.jets.server.business.services.MessageService;
import gov.iti.jets.server.persistance.entities.MessageEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;
import java.util.ArrayList;
import java.util.List;

public class MessageServiceImpl implements MessageService {

	@Override
	public List<MessageDto> getAllMessagesByGroup(Integer groupId) {
		MessageDao messageDao = DaosFactory.INSTANCE.getMessageDao();
		List<MessageEntity> messageEntities = messageDao.getAllMessagesByGroup(groupId);
		List<MessageDto> messageDtos = new ArrayList<>();
		// TODO: map messageEntities to messageDtos
		return messageDtos;
	}

}
