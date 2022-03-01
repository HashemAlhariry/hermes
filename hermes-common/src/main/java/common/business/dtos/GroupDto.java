package common.business.dtos;

import java.io.Serializable;

import common.business.util.OnlineStatus;

public class GroupDto implements Serializable {
	public int id;
	public String name;
	public byte[] image;
	public OnlineStatus status;

	public GroupDto(int id, String name, byte[] image, OnlineStatus status) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
	}
}
