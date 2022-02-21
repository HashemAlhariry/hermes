package gov.iti.jets.server.persistance.entities;

public class UserEntity {
	public String phoneNumber;
	public String password;
	public String name;
	public String email;
	public Boolean gender;
	public String country;
	public String dateOfBirth;
	public String bio;

	public UserEntity() {

	}

	public UserEntity(String phone,
			String password,
			String name,
			String email,
			Boolean gender,
			String country,
			String dateOfBirth,
			String bio) {
		this.phoneNumber = phone;
		this.password = password;
		this.country = country;
		this.gender = gender;
		this.email = email;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.bio = bio;
	}

}
