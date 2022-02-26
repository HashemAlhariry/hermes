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

	public void setUserModel(UserModel userModel){
		//this.userModel.getPhoneNumber()=userModel
		
	}

}
