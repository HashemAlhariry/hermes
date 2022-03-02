package gov.iti.jets.client.presentation.models;

import gov.iti.jets.client.presentation.controllers.Contact;
import gov.iti.jets.client.presentation.util.SimpleObservable;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;


public class MessageModel {

    public String content;
    public Date sendDate;
    public int groupId;
    public String senderPhone;
    protected final SimpleObservable observable = new SimpleObservable();

    public Observable getObservable() {
        return observable;
    }

    public static <T extends MessageModel> ObservableList<T> observableArrayList() {
        return FXCollections.observableArrayList(e -> new Observable[] { e.observable });
    }

    public void update() {
        observable.invalidate();
    }

    public MessageModel(String content, Date sendDate, int groupId, String senderPhone) {
        this.content = content;
        this.sendDate = sendDate;
        this.groupId = groupId;
        this.senderPhone = senderPhone;
    }

}
