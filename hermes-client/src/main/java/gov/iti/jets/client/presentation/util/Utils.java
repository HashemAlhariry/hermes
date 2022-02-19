package gov.iti.jets.client.presentation.util;

import java.util.regex.Pattern;

public enum Utils {
    INSTANCE;

    
    public boolean checkNumberInString(String phoneNumber){
        Pattern pattern = Pattern.compile(".*[^0-9].*");
        return !pattern.matcher(phoneNumber).matches();
      }
      
}
