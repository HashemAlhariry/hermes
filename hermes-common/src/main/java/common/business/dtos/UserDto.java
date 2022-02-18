package common.business.dtos;

import java.io.Serializable;

public class UserDto implements Serializable {
    String phone;
    String password;
    public UserDto(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

}
