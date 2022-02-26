package gov.iti.jets.server.business.services.impl;

import common.business.dtos.GroupDetailsDto;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.persistance.entities.GroupEntity;
import gov.iti.jets.server.persistance.entities.GroupUserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;

import java.io.FileOutputStream;
import java.io.IOException;

public class GroupServiceImpl implements GroupService {
    @Override
    public void addNewGroupChat(GroupDetailsDto groupDetailsDto) {

        GroupEntity groupEntity = new GroupEntity(0, groupDetailsDto.groupFullName, groupDetailsDto.imageName,
                groupDetailsDto.contacts.size() + 1);
        // insert new group to the db
        int check = DaosFactory.INSTANCE.getGroupDao().insertGroup(groupEntity);
        if (check == 1) {
            // get the id of the
            int groupID = DaosFactory.INSTANCE.getGroupDao().getGroupId(groupEntity);
            System.out.println(groupID);

            // create new group_user for user creator
            GroupUserEntity groupUserEntity = new GroupUserEntity(groupID, groupDetailsDto.creatorPhone);
            DaosFactory.INSTANCE.getGroupUserDao().insertGroupUser(groupUserEntity);

            // create new group_user for each contact
            for (int i = 0; i < groupDetailsDto.contacts.size(); i++) {
                groupUserEntity = new GroupUserEntity(groupID, groupDetailsDto.contacts.get(i));
                DaosFactory.INSTANCE.getGroupUserDao().insertGroupUser(groupUserEntity);
            }

            try (FileOutputStream fos = new FileOutputStream(
                    "hermes-server/src/main/resources/images/" + groupDetailsDto.groupFullName)) {
                fos.write(groupDetailsDto.image);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
