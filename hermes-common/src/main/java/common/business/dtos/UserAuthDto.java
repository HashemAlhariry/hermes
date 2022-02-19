package common.business.dtos;

import java.io.Serializable;

public class UserAuthDto implements Serializable {
    
    public String phoneNumber;
    public String password;


    public UserAuthDto(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
