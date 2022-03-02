package gov.iti.jets.server.business.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import common.business.dtos.GroupDto;
import common.business.dtos.UserDto;
import common.business.util.OnlineStatus;
import gov.iti.jets.server.business.daos.GroupDao;
import common.business.dtos.GroupDetailsDto;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.persistance.entities.GroupEntity;
import gov.iti.jets.server.persistance.entities.GroupUserEntity;
import gov.iti.jets.server.persistance.entities.UserEntity;
import gov.iti.jets.server.persistance.util.DaosFactory;

public class GroupServiceImpl implements GroupService {

	@Override
	public List<GroupDto> getAllGroupsByUserWithChatType(UserDto userDto) {
		GroupDao groupDao = DaosFactory.INSTANCE.getGroupDao();
		UserEntity userEntity = UserMapperImpl.INSTANCE.mapFromUserDto(userDto);
		List<GroupEntity> groupEntities = groupDao.getAllGroupdByUser(userEntity);
		List<GroupDto> groupDtos = new ArrayList<>();

		// if a private group (has only 2) then fetch name and image of the other peer
		groupEntities.forEach((gE) -> {
			try {
				OnlineStatus status;
				if (gE.participantsNumber == 2) {
					String receiverPhone = DaosFactory.INSTANCE.getGroupDao()
							.getUsersByGroupId((long) gE.id)
							.stream()
							.filter(i -> !i.equals(userDto.phoneNumber)).findFirst().get();
					System.out.println(userDto.phoneNumber + " is checking for " + receiverPhone);
					UserEntity receiver = DaosFactory.INSTANCE.getUserDao().getUserByPhone(receiverPhone).get();
					gE.name = receiver.name;
					gE.image = receiver.image;
					status = OnlineStatus.OFFLINE; // the default
					fillDto(groupDtos, gE, status);
				} else {
					status = OnlineStatus.GROUP;
					fillDto(groupDtos, gE, status);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return groupDtos;
	}

	private void fillDto(List<GroupDto> groupDtos, GroupEntity gE, OnlineStatus onlineStatus) throws IOException {

		BufferedImage img;
	 	if(gE.image==null){
			 gE.image = "person.png" ;
		}
		img = ImageIO.read(getClass().getResource("/userImages/" + gE.image));
		ByteArrayOutputStream baos  = new ByteArrayOutputStream();
		ImageIO.write(img, gE.image.split("\\.")[1], baos);
		groupDtos.add(new GroupDto(gE.id, gE.name, baos.toByteArray(), onlineStatus));

	}

	@Override
	public void createGroup(GroupDto groupDto) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getPrivateChatPeerPhone(int groupId, UserDto userDto) {
		GroupDao groupDao = DaosFactory.INSTANCE.getGroupDao();
		List<String> usersPhones = groupDao.getUsersByGroupId((long) groupId);
		return usersPhones.stream().filter(u -> !u.equals(userDto.phoneNumber)).findFirst().get();
	}

    @Override
    public void addNewGroupChat(GroupDetailsDto groupDetailsDto) {

        GroupEntity groupEntity = new GroupEntity(0, groupDetailsDto.groupFullName, groupDetailsDto.imageName,
                groupDetailsDto.contacts.size() + 1);

        // check group size more than 2 contacts
        if( groupDetailsDto.contacts.size() > 1) {

            // insert new group to the db
            int check = DaosFactory.INSTANCE.getGroupDao().insertGroup(groupEntity);

            //checking that group created successfully
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
                        "hermes-server/src/main/resources/userImages/" + groupDetailsDto.imageName)) {
                    fos.write(groupDetailsDto.image);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public List<GroupDto> getAllGroupsByUser(UserDto userDto) {
        return null;
    }

}
