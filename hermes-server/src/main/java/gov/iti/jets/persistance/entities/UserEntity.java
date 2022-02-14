package gov.iti.jets.persistance.entities;

public class UserEntity {
	public String phone;
	public String name;

	public UserEntity() {

	}

	public UserEntity(String phone, String name) {
		this.phone = phone;
		this.name = name;
	}

	@Override
	public String toString() {
		return name + " : " + phone;
	}
}
