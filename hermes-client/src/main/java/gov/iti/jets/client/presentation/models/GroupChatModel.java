package gov.iti.jets.client.presentation.models;

import java.util.List;

import common.business.dtos.MessageDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GroupChatModel {

    private StringProperty groupID = new SimpleStringProperty();
    private ObservableList<MessageDto> messageDtos = FXCollections.observableArrayList();

    public String getGroupID() {
        return groupID.get();
    }

    public  List<MessageDto> getMessageDtos() {
        return messageDtos;
    }
    
    public void setGroupID(String groupID) {
        this.groupID.set(groupID) ;
    }
    
    public void setMessageDtos(List<MessageDto> messageDtos) {
        this.messageDtos.setAll(messageDtos);
    }

    public StringProperty groupIdProperty(){
        return groupID;
    }

    public ObservableList<MessageDto> messageDtosProperty(){
            return messageDtos;
    }
}

