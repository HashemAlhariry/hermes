package gov.iti.jets.client.presentation.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserModel {
// 	(Phone Number, Display Name, email, picture, password, confirmation password, 
// gender, country, date of birth, bio.)

	private StringProperty userName = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty phoneNumber = new SimpleStringProperty();
	private ObjectProperty picture = new SimpleObjectProperty();
	private StringProperty gender = new SimpleStringProperty();
	private StringProperty country = new SimpleStringProperty();
	private StringProperty dateOfBirth = new SimpleStringProperty();
	private StringProperty bio = new SimpleStringProperty();

	public String getUserName() {
		return userName.get();
	}

	public StringProperty userNameProperty() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public String getPassword() {
		return password.get();
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public String getEmail() {
		return email.get();
	}

	public StringProperty emailProperty() {
		return email;
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public StringProperty phoneNumberProperty() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	public StringProperty getBio() {
		return bio;
	}

	public void setBio(StringProperty bio) {
		this.bio = bio;
	}

	public void setBio(String bio) {
		this.bio.set(bio);
	}

	public StringProperty getCountry() {
		return country;
	}

	public void setCountry(StringProperty country) {
		this.country = country;
	}

	public void setCountry(String country) {
		this.country.set(country);
	}

	public StringProperty getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(StringProperty dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}

	public StringProperty getGender() {
		return gender;
	}

	public void setGender(StringProperty gender) {
		this.gender = gender;
	}

	public void setGender(String gender) {
		this.gender.set(gender);
	}

	public ObjectProperty getPicture() {
		return picture;
	}

	public void setPicture(ObjectProperty picture) {
		this.picture = picture;
	}

	public void setPicture(Object picture) {
		this.picture.set(picture);
	}
}
