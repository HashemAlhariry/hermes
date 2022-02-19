package gov.iti.jets.client.presentation.util.validation;

public enum Validators {
    INSTANCE;

	public boolean isEmpty(String toValidate) {
		return toValidate == null || toValidate.isBlank();
	}

}
