package common.business.dtos;

import java.io.Serializable;

public class PrivateGroupDetailsDto implements Serializable {

   public  String firstContact;
   public  String secondContact;
   public  String uniqueName;

   public PrivateGroupDetailsDto(String firstContact, String secondContact, String uniqueName) {
        this.firstContact = firstContact;
        this.secondContact = secondContact;
        this.uniqueName = uniqueName;
   }

}
