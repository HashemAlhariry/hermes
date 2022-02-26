package gov.iti.jets.server.business.services;

import common.business.dtos.GroupDto;
import common.business.dtos.UserDto;
import java.util.List;

public interface GroupService {
	public void addNewGroupChat(GroupDetailsDto groupDetailsDto);

	List<GroupDto> getAllGroupsByUser(UserDto userDto);

	void createGroup(GroupDto groupDto);
}
