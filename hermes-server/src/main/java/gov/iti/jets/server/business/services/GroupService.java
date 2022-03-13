package gov.iti.jets.server.business.services;

import common.business.dtos.GroupDetailsDto;
import common.business.dtos.GroupDto;
import common.business.dtos.UserDto;

import java.io.IOException;
import java.util.List;

public interface GroupService {

	List<GroupDto> getAllGroupsByUserWithChatType(UserDto userDto);

	String getPrivateChatPeerPhone(int groupId, UserDto userDto);

	public void addNewGroupChat(GroupDetailsDto groupDetailsDto);

	List<GroupDto> getAllGroupsByUser(UserDto userDto);

	void createGroup(GroupDto groupDto);
}
