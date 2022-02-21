package common.business.dtos;

import java.io.Serializable;

public class UserDto implements Serializable {
    // (Phone Number, Display Name, email, picture, password, confirmation password,
    // gender, country, date of birth, bio.
    public String phoneNumber;
    public String name;
    public String email;
    public String password;
    public String picture; 
    public String gender;
    public String dateOfBirth;
    public String country;
    public String bio;

    public UserDto(String phoneNumber, String name, String email, String password, String picture, String gender,
            String dateOfBirth, String country, String bio) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.bio = bio;
    }
 

}
