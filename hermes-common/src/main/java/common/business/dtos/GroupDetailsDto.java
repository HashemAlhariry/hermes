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

    public GroupDetailsDto(String groupName, String groupFullName, String creatorPhone, List<String> contacts, byte[] image, String imageName) {
        this.groupName = groupName;
        this.groupFullName = groupFullName;
        this.creatorPhone = creatorPhone;
        this.contacts = contacts;
        this.image = image;
        this.imageName = imageName;
    }

}
