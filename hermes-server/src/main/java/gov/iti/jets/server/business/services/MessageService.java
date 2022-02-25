package gov.iti.jets.server.business.services;

import common.business.dtos.MessageDto;
import java.util.List;

public interface MessageService {

	List<MessageDto> getAllMessagesByGroup(Integer groupId);

}
