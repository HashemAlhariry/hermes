package gov.iti.jets.server.business.services.impl;

import common.business.dtos.MessageDto;
import gov.iti.jets.server.business.daos.MessageDao;
import gov.iti.jets.server.business.services.MessageService;
import gov.iti.jets.server.persistance.entities.MessageEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;
import java.util.ArrayList;
import java.util.List;

public class MessageServiceImpl implements MessageService {

	private MessageDao messageDao = DaosFactory.INSTANCE.getMessageDao();

	@Override
	public List<MessageDto> getAllMessagesByGroup(Integer groupId) {
		List<MessageEntity> messageEntities = messageDao.getAllMessagesByGroup(groupId);
		List<MessageDto> messageDtos = new ArrayList<>();
		messageEntities.forEach( messageEntity -> messageDtos.add(MessageMapperImpl.INSTANCE.mapToMessageDto(messageEntity)));
		return messageDtos;
	}

	@Override
	public void insertMessage(MessageDto messageDto) {
		MessageEntity messageEntity = MessageMapperImpl.INSTANCE.mapFromMessageDto(messageDto);
		messageDao.insertMessage(messageEntity);
	}


}
