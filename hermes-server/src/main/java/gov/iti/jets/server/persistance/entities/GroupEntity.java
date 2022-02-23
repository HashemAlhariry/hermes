package gov.iti.jets.server.persistance.entities;

public class GroupEntity {

	public int id;
	public String name;
	public String image;
	public int participantsNumber;

	public GroupEntity() {
	}

	public GroupEntity(int id, String name, String image, int participantsNumber) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.participantsNumber = participantsNumber;
	}
}
