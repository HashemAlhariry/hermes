package common.business.dtos;

import java.io.Serializable;
import java.util.List;

public class GroupDetailsDto implements Serializable {

    public String groupName;
    //user who created the group + the time where groupCreated at
    public String groupFullName;
    public String creatorPhone;
    public List<String> contacts;
    public byte[] image;
    public String imageName;


}
