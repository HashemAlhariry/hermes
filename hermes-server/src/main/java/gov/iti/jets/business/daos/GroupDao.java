package gov.iti.jets.business.daos;

import java.util.List;

import gov.iti.jets.persistance.entities.GroupEntity;
import gov.iti.jets.persistance.entities.UserEntity;

public interface GroupDao {
	
	List<GroupEntity> getAllGroupdByUser(UserEntity userEntity);
	void insertGroup(GroupEntity groupEntity);
	void updateGroup(GroupEntity groupEntity);
	void deleteGroup(GroupEntity groupEntity);
	
}
