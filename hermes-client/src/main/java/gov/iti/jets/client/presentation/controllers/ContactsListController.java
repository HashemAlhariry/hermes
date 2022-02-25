package gov.iti.jets.client.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ContactsListController implements Initializable{
    @FXML
    private ListView<String> ContactsListView;

    private ObservableList<String> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("from list intializer");
        ContactsListView.setItems(observableList);
        for(int x=0; x<2; x++) {
            observableList.add(x+"");
        }
        observableList.add("amira");
        System.out.println(ContactsListView);
    }
}
