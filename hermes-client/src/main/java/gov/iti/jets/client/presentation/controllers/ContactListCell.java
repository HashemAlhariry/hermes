package gov.iti.jets.client.presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ContactListCell extends ListCell<Contact> {
	@FXML
	private Label contactName;

	@FXML
	private ImageView contactImage;

	@FXML
	private HBox hbox;

	private FXMLLoader mLLoader;

	@Override
	protected void updateItem(Contact item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			setText(null);
			setGraphic(null);
			return;
		}
		if (mLLoader == null) {
			mLLoader = new FXMLLoader(getClass().getResource("/views/homepage/contactListItem.fxml"));
			mLLoader.setController(this);
			try {
				mLLoader.load();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		contactName.setText(item.name);
		contactImage.setImage(item.image);

		setText(null);
		setGraphic(hbox);
	}

}
