package common.business.dtos;

import java.io.Serializable;
import java.time.LocalDate;


public class UserDto implements Serializable {
	// (Phone Number, Display Name, email, picture, password, confirmation password,
	// gender, country, date of birth, bio.

	public String phoneNumber;
	public String password;
	public String name;
	public String email;
	public Boolean gender;
	public String country;
	public LocalDate dateOfBirth;
	public String bio;
	public byte[] image;

	public UserDto(
		String phone,
		String password,
		String name,
		String email,
		Boolean gender,
		LocalDate dateOfBirth,
		String country,
		String bio
	) {
		this.phoneNumber = phone;
		this.password = password;
		this.country = country;
		this.gender = gender;
		this.email = email;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.bio = bio;
	}
	public byte[] getImage(){
		return image;
	}

}
