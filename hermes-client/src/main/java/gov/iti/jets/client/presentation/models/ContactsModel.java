package gov.iti.jets.client.presentation.models;

import java.util.List;

import gov.iti.jets.client.presentation.controllers.Contact;
import javafx.collections.ObservableList;

/**
 * ContactsModel
 */
public class ContactsModel {

	private ObservableList<Contact> contacts = Contact.observableArrayList();

	public ObservableList<Contact> contactsProperty() {
		return contacts;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts.addAll(contacts);
	}

}