package gov.iti.jets.client.presentation.controllers;

import common.business.util.OnlineStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ContactListCell extends ListCell<Contact> {
	@FXML
	private Label contactName;

	@FXML
	private ImageView contactImage;

	@FXML
	private HBox contactItemHbox;

	@FXML
	private Circle userStatusCircle;

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
		Rectangle rectangle = new Rectangle(contactImage.getFitWidth(), contactImage.getFitHeight());
		rectangle.setArcHeight(20);
		rectangle.setArcWidth(20);
		contactImage.setClip(rectangle);
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		WritableImage image = contactImage.snapshot(parameters, null);
		// remove the rounding clip so that our effect can show through.
		contactImage.setClip(null);
		// apply a shadow effect.
		contactImage.setEffect(new DropShadow(20, Color.TRANSPARENT));
		// store the rounded image in the imageView.
		contactImage.setImage(image);

		// set circle color accoriding to status if Private and remove it if group
		OnlineStatus onlineStatus = item.status;
		if (onlineStatus == OnlineStatus.AVAILABLE) {
			// #37ff03
			userStatusCircle.setFill(Color.rgb(55, 255, 3));
			System.out.println(contactName + " is available");
		} else if (onlineStatus == OnlineStatus.AWAY) {
			// #ffb603
			userStatusCircle.setFill(Color.rgb(255, 182, 3));
			System.out.println(contactName + " is away");
		} else if (onlineStatus == OnlineStatus.BUSY) {
			// #ff1414
			userStatusCircle.setFill(Color.rgb(255, 20, 20));
			System.out.println(contactName + " is busy");
		} else if (onlineStatus == OnlineStatus.OFFLINE) {
			userStatusCircle.setFill(Color.GREY);
			System.out.println(contactName + " is offline");
		} else if (onlineStatus == OnlineStatus.GROUP) {
			userStatusCircle.setVisible(false);
		}
		setText(null);
		setGraphic(contactItemHbox);
	}

}
