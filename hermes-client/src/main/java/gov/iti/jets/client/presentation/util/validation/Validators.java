package gov.iti.jets.client.presentation.util.validation;

public enum Validators {
    INSTANCE;

	public boolean isEmpty(String toValidate) {
		return toValidate == null || toValidate.isBlank();
	}
	public boolean isContainCharacters(String password){
		if(password.matches("[a-zA-Z]+"))
			return true;
		return false;
	}
	public boolean isContainNumbers(String password){
		if (password.matches("[0-9]+"))
			return true;
		return false;
	}
	

}
