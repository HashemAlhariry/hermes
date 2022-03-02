package gov.iti.jets.server.business.daos;

import gov.iti.jets.server.persistance.entities.GroupUserEntity;

import java.util.List;

public interface GroupUserDao {
    int insertGroupUser(GroupUserEntity groupUserEntity);
    public List<String> getAllUsersByGroup(int groupID);
}
