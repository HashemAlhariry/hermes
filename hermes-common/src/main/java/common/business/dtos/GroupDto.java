package common.business.dtos;

import java.io.Serializable;

public class GroupDto implements Serializable {
	public int id;
	public String name;
	public byte[] image;
	
	public GroupDto(int id, String name, byte[] image) {
		this.id = id;
		this.name = name;
		this.image = image;
	}
}
