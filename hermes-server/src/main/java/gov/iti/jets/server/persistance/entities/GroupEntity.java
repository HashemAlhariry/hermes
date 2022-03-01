package gov.iti.jets.server.persistance.entities;

public class GroupEntity {

    private int id;
    private String name;
    private String image;
    private int participantsNumber;

    public GroupEntity(int id, String name, String image, int participantsNumber) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.participantsNumber = participantsNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }
}
