package gov.iti.jets.presentation.util;

import gov.iti.jets.presentation.models.UserModel;

public enum ModelsFactory {

	INSTANCE;

	private UserModel userModel = new UserModel();

	private ModelsFactory() {

	}

	public UserModel getUserModel() {
		return userModel;
	}

}
