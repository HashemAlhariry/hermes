package gov.iti.jets.server.business.services.impl;

import common.business.dtos.PrivateGroupDetailsDto;
import gov.iti.jets.server.business.services.PrivateGroupService;
import gov.iti.jets.server.persistance.entities.GroupEntity;
import gov.iti.jets.server.persistance.entities.GroupUserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;

public class PrivateGroupServiceImpl implements PrivateGroupService {

    @Override
    public int addNewPrivateGroupChat(PrivateGroupDetailsDto privateGroupDetailsDto) {
        int groupID = -1;
        GroupEntity groupEntity = new GroupEntity(0,privateGroupDetailsDto.uniqueName,"Default",2);
        //insert new group to the db
       int check = DaosFactory.INSTANCE.getGroupDao().insertGroup(groupEntity);
       if(check==1){
           //get the id of the
              groupID =  DaosFactory.INSTANCE.getGroupDao().getGroupId(groupEntity);
              System.out.println(groupID);
              // create new group user for each contact
           GroupUserEntity groupUserEntity1 = new GroupUserEntity(groupID,privateGroupDetailsDto.firstContact);
           GroupUserEntity groupUserEntity2 = new GroupUserEntity(groupID, privateGroupDetailsDto.secondContact);
           // save both to database
           int firstContactCheck= DaosFactory.INSTANCE.getGroupUserDao().insertGroupUser(groupUserEntity1);
           if(firstContactCheck==1){
               DaosFactory.INSTANCE.getGroupUserDao().insertGroupUser(groupUserEntity2);
           }

       }
       return groupID;
    }
}
