package gov.iti.jets.client.presentation.util.validation;

public enum Messages {
	INSTANCE;
    public final String USER_NAME_EMPTY = "Username can't be empty";
	public final String INVALID_USER_NAME = "Username must be at least 3 characters and maximum 50 character";
	public final String EMAIL_EMPTY = "Email can't be empty";
	public final String INVALID_EMAIL_FORMAT = "Invalid email format";
	public final static String INVALID_PASSWORD_FORMAT = "Password must contain characters and digits";
	public final static String PASSWORD_EMPTY = "Password can't be empty";

	public final String INVALID_DATE = "Date of birth is not valid";
	public final static String PASSWORDS_MUST_MATCH = "Password fields need to be identical";
	public final static String PASSWORDS_MUST_MORETHAN_7 = "Password must contain less than 7 characters";
	public final static String PHONE_MUST_CONTAIN_NUMBERS_ONLY="Phone must contain numbers only";
	public final static String PHONE_MUSTNOT_CONTAIN_SPACES="Phone must not contain spaces";
	public final static String PHONE_MUST_CONTAIN_11_NUMBER="Phone must contain 11 numbers";
}
