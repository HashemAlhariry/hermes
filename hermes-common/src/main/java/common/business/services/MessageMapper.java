package common.business.services;

import common.business.dtos.MessageDto;

public interface MessageMapper<T> {
    public T mapFromMessageDto(MessageDto messageDto);

    public MessageDto mapToMessageDto(T message);
}
