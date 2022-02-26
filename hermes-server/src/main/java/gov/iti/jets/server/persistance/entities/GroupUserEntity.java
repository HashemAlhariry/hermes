package gov.iti.jets.server.persistance.entities;

public class GroupUserEntity {

    private int groupIdFk;
    private String userPhoneFk;


    public GroupUserEntity(int groupIdFk, String userPhoneFk) {
        this.groupIdFk = groupIdFk;
        this.userPhoneFk = userPhoneFk;
    }

    public int getGroupIdFk() {
        return groupIdFk;
    }

    public void setGroupIdFk(int groupIdFk) {
        this.groupIdFk = groupIdFk;
    }

    public String getUserPhoneFk() {
        return userPhoneFk;
    }

    public void setUserPhoneFk(String userPhoneFk) {
        this.userPhoneFk = userPhoneFk;
    }
}
