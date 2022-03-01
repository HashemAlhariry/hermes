package gov.iti.jets.server.business.services.impl;

import common.business.dtos.MessageDto;
import common.business.services.MessageMapper;
import gov.iti.jets.server.persistance.entities.MessageEntity;

public enum MessageMapperImpl implements MessageMapper<MessageEntity> {

    INSTANCE;
    @Override
    public MessageEntity mapFromMessageDto(MessageDto messageDto) {
        if (messageDto == null)
            return null;
        return new MessageEntity(messageDto.content, messageDto.sendDate, messageDto.receiverId, messageDto.senderPhone);
    }

    @Override
    public MessageDto mapToMessageDto(MessageEntity message) {
        if (message == null)
        return null;
    return new MessageDto(message.content, message.sendDate, message.receiverId, message.senderPhone);
}

}
