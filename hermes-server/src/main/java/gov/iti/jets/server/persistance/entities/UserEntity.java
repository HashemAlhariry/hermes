package gov.iti.jets.server.persistance.entities;

import java.sql.Date;

public class UserEntity {
	private String phone;
	private String name;
	private String email;
	private String password;
	private String image;
	private boolean gender;
	private Date dob;
	private String country;
	private String bio;

	public UserEntity(String phone, String name, String email, String password, String image, boolean gender, Date dob,
			String country, String bio) {
		this.phone = phone;
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
		this.gender = gender;
		this.dob = dob;
		this.country = country;
		this.bio = bio;
	}

	public UserEntity() {

	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public String toString() {
		return name + " : " + phone;
	}
}
