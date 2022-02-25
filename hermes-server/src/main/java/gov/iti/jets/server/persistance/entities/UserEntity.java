
package gov.iti.jets.server.persistance.entities;

import java.sql.Date;

public class UserEntity {

	public String phone;
	public String name;
	public String email;
	public String password;
	// public String image; //to be implemented later
	public boolean gender;
	public Date dob;
	public String country;
	public String bio;

	public UserEntity(){}

	public UserEntity(String phone, String name, String email, String password, boolean gender, Date dob,
			String country, String bio) {
		this.phone = phone;
		this.name = name;
		this.email = email;
		this.password = password;
		// this.image = image;
		this.gender = gender;
		this.dob = dob;
		this.country = country;
		this.bio = bio;
	}

	public UserEntity(String password, String phone){
		this.password = password;
		this.phone = phone;
	}
	@Override
	public String toString() {
		return name + " : " + phone;
	}
}
