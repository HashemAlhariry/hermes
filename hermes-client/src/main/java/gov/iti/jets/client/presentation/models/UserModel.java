package gov.iti.jets.client.presentation.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class UserModel {
// 	(Phone Number, Display Name, email, picture, password, confirmation password, 
// gender, country, date of birth, bio.)

	private StringProperty userName = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty phoneNumber = new SimpleStringProperty();
	private Property<Image> picture = new SimpleObjectProperty<>();
	private BooleanProperty gender = new SimpleBooleanProperty();
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


	public StringProperty bioProperty() {
		return bio;
	}

	public String getBio() {
		return bio.get();
	}

	public void setBio(StringProperty bio) {
		this.bio = bio;
	}

	public void setBio(String bio) {
		this.bio.set(bio);
	}

	public StringProperty countryProperty() {
		return country;
	}

	public String getCountry() {
		return country.get();
	}

	public void setCountry(StringProperty country) {
		this.country = country;
	}

	public void setCountry(String country) {
		this.country.set(country);
	}

	public StringProperty dateOfBirthProperty() {
		return dateOfBirth;
	}

	public String getDateOfBirth() {
		return dateOfBirth.get();
	}

	public void setDateOfBirth(StringProperty dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}

	public BooleanProperty genderpProperty() {
		return gender;
	}

	public Boolean getGender() {
		return gender.get();
	}

	public void setGender(BooleanProperty gender) {
		this.gender = gender;
	}

	public void setGender(Boolean gender) {
		this.gender.set(gender);
	}

	public Property<Image> picturepProperty() {
		return picture;
	}

	public Image getPicture() {
		return picture.getValue();
	}

	public void setPicture(Image picture) {
		this.picture.setValue(picture);
	}
}
