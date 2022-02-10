package gov.iti.jets.presentation.util;

import gov.iti.jets.presentation.models.UserModel;

public class ModelsFactory {

	private final static ModelsFactory modelsFactory = new ModelsFactory();
	private UserModel userModel = new UserModel();

	private ModelsFactory() {

	}

	public static ModelsFactory getInstance() {
		return modelsFactory;
	}

	public UserModel getUserModel() {
		return userModel;
	}

}
