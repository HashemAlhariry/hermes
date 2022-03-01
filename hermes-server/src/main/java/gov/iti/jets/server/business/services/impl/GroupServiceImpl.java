package gov.iti.jets.server.business.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import common.business.dtos.GroupDto;
import common.business.dtos.UserDto;
import common.business.util.OnlineStatus;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.business.services.GroupService;
import gov.iti.jets.server.persistance.entities.GroupEntity;
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
		var img = ImageIO.read(getClass().getResource("/userImages/" + gE.image));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, gE.image.split("\\.")[1], baos);
		// null because no status is set here
		groupDtos.add(new GroupDto(gE.id, gE.name, baos.toByteArray(), onlineStatus));
	}

	// 01285097233.png

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

}
