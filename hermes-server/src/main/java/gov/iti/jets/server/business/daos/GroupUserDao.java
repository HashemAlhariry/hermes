package gov.iti.jets.server.business.daos;

import gov.iti.jets.server.persistance.entities.GroupUserEntity;

public interface GroupUserDao {
    int insertGroupUser(GroupUserEntity groupUserEntity);
}
