package common.business.dtos;

import java.io.Serializable;

public class UserConnectedDto implements Serializable{

   public String phone;
   public String name;
   
   public UserConnectedDto(String phone, String name) {
    super();
    this.phone = phone;
    this.name = name;
}



  
}
