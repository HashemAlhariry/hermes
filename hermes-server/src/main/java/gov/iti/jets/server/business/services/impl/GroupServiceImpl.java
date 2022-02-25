package gov.iti.jets.server.business.services.impl;

import common.business.dtos.GroupDto;
import common.business.dtos.UserDto;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.persistance.entities.GroupEntity;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;
import java.util.ArrayList;
import java.util.List;

public class GroupServiceImpl implements GroupService {

	@Override
	public List<GroupDto> getAllGroupsByUser(UserDto userDto) {
		GroupDao groupDao = DaosFactory.INSTANCE.getGroupDao();
		// TODO: here goes the mapping from
		UserEntity userEntity = null;
		List<GroupEntity> groupEntities = groupDao.getAllGroupdByUser(userEntity);
		List<GroupDto> groupDtos = new ArrayList<>();
		//TODO: map groupEntities to groupDtos
		return groupDtos;
	}

	@Override
	public void createGroup(GroupDto groupDto) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
