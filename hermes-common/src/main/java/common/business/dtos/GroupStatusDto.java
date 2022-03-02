package common.business.dtos;

import java.io.Serializable;

import common.business.util.OnlineStatus;

public class GroupStatusDto implements Serializable {

	public int id;
	public OnlineStatus onlineStatus;
	
	public GroupStatusDto(int id, OnlineStatus onlineStatus) {
		this.id = id;
		this.onlineStatus = onlineStatus;
	}
}
