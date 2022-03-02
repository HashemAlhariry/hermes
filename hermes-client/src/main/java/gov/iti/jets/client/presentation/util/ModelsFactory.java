package gov.iti.jets.client.presentation.util;

import gov.iti.jets.client.presentation.models.UserModel;

public enum ModelsFactory {

	INSTANCE;

	private UserModel userModel = new UserModel();

	private ModelsFactory() {

	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		// this.userModel.setPhoneNumber(userModel.getPhoneNumber());
		// this.userModel.setUserName(userModel.getUserName());
		// this.userModel.setEmail(userModel.getEmail());
		// this.userModel.setPassword(userModel.getPassword());
		// this.userModel.setPicture(userModel.getPicture());
		// this.userModel.setGender(userModel.getGender());
		// this.userModel.setDateOfBirth(userModel.getDateOfBirth());
		// this.userModel.setCountry(userModel.getCountry());
		// this.userModel.setBio(userModel.getBio());
		this.userModel = userModel;
	}

}
