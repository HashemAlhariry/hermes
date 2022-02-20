package gov.iti.jets.client.presentation.util.validation;

public enum Messages {
	INSTANCE;
    public final String USER_NAME_EMPTY = "Username can't be empty";
	public final String INVALID_USER_NAME = "Username must be at least 3 characters and maximum 50 character";
	public final String EMAIL_EMPTY = "Email can't be empty";
	public final String INVALID_EMAIL_FORMAT = "Invalid email format";
	public final static String INVALID_PASSWORD_FORMAT = "Password must contain characters and digits";
	public final static String PASSWORD_EMPTY = "Password can't be empty";
	public final String PASSWORDS_MUST_MATCH = "Password fields need to be identical";
	public final String INVALID_DATE = "Date of birth is not valid";


}
