package gov.iti.jets.client.presentation.util.validation;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


	public boolean isEmailValidFormat(String email) {
		var p = Pattern.compile(
				"[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");
                Matcher matcher = p.matcher(email);
		return matcher.matches();
    }

	public boolean isUserNameValid(String username) {
		var p = Pattern.compile(
				"^(([a-zA-Z]){2,}[\u0020]?){1,20}$");
		Matcher matcher = p.matcher(username);
		return matcher.matches();
	}

	public boolean isUserNameContainsSpaces(String username){
		return username.contains(" ");
	}

	public boolean isDateOfBirthNotValid(LocalDate date){
		if (date == LocalDate.now() || (LocalDate.now().getYear() - date.getYear()) < 6 || (LocalDate.now().getYear() - date.getYear()) > 100) {
			return true;
		}
		return false;
	}


	
}
