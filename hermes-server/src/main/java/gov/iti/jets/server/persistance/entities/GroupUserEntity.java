package gov.iti.jets.server.persistance.entities;

public class GroupUserEntity {

    public int groupIdFk;
    public String userPhoneFk;


    public GroupUserEntity(int groupIdFk, String userPhoneFk) {
        this.groupIdFk = groupIdFk;
        this.userPhoneFk = userPhoneFk;
    }

}
