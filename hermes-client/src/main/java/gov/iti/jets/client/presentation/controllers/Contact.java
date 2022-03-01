package gov.iti.jets.client.presentation.controllers;

import common.business.util.OnlineStatus;
import gov.iti.jets.client.presentation.util.SimpleObservable;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Contact {
	public int groupId;
	public String name;
	public Image image;
	public OnlineStatus status;
	protected final SimpleObservable observable = new SimpleObservable();

	public Observable getObservable() {
		return observable;
	}

	public static <T extends Contact> ObservableList<T> observableArrayList() {
		return FXCollections.observableArrayList(e -> new Observable[] { e.observable });
	}

	public void update() {
		observable.invalidate();
	}

	public Contact(int groupId, String name, Image image, OnlineStatus status) {
		this.groupId = groupId;
		this.name = name;
		this.image = image;
		this.status = status;
	}

}
