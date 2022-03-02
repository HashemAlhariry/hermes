package gov.iti.jets.client.presentation.util;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import common.business.dtos.InvitationResponseDto;
import common.business.dtos.MessageDto;
import common.business.dtos.PrivateGroupDetailsDto;
import gov.iti.jets.client.presentation.models.ContactsModel;
import gov.iti.jets.client.presentation.models.MessageModel;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;

public enum Utils {

	INSTANCE;

	public BooleanProperty booleanProperty = new SimpleBooleanProperty(true);

	public int currentOpenGroupId;
	public boolean checkNumberInString(String phoneNumber) {
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		return !pattern.matcher(phoneNumber).matches();
	}

	public void invitationNotification(String sender) {
		System.out.println("FROM UTILS TO SHOW INVITATION DIALOG FOR" + sender);
		Platform.runLater(() -> {

			Alert alertBox = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", ButtonType.OK,
					ButtonType.CANCEL);
			alertBox.setTitle("Invitation Message");
			alertBox.setContentText("A Client Whos Number is " + sender + " is trying you to add you");
			alertBox.initModality(Modality.APPLICATION_MODAL);
			alertBox.initOwner(StageCoordinator.INSTANCE.getPrimaryStage());
			alertBox.showAndWait();

			if (alertBox.getResult() == ButtonType.OK) {

				try {
					LocalDateTime myObj = LocalDateTime.now();
					RMIConnection.INSTANCE.getServer().invitationResponse(new InvitationResponseDto(sender,
							ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(), 1));
					// CALL SERVICE TO ADD A PRIVATE CHAT TO DATA BASE BETWEEN SENDER AND RECEIVER
					RMIConnection.INSTANCE.getServer()
							.addPrivateChat(new PrivateGroupDetailsDto(sender,
									ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(),
									ModelsFactory.INSTANCE.getUserModel().getPhoneNumber() + "_" + myObj));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				System.out.println("YES");

			} else {

				// DO NOTHING
				// update invitation request to be rejected
				try {
					RMIConnection.INSTANCE.getServer().invitationResponse(new InvitationResponseDto(sender,
							ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(), 2));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				System.out.println("NO");
				alertBox.close();
			}
		});

	}
	public void receiveBroadCastMessage(String broadCastMessage) {
		Platform.runLater(() -> {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Server Announcemenet");
			WebView webView = new WebView();
			webView.getEngine().loadContent(broadCastMessage);
			webView.setPrefSize(600, 200);
			alert.getDialogPane().setContent(webView);
			alert.showAndWait();

		});
	}

	public void appendMessagesToVbox(MessageDto messageDto){
		if(currentOpenGroupId==messageDto.groupId){
			//append elmessege
			//StageCoordinator.INSTANCE.getPrimaryStage().
			Platform.runLater( ()->{
				    ImageView imageView=new ImageView();
					imageView.setFitHeight(20);
					imageView.setFitWidth(20);

					Scene scene = StageCoordinator.INSTANCE.getPrimaryStage().getScene();
					VBox messagesVerticalBox = (VBox) scene.lookup("#messagesVerticalBox");

					var formattedMessage = HTMLMessageParser.INSTANCE.formatMessage(messageDto.content,  new Timestamp(messageDto.sendDate.getTime()));
					HBox messageHorizontalBox = new HBox();
					ModelsFactory.INSTANCE.getContactsModel().getContacts().forEach((item)->{
						if(messageDto.groupId==item.groupId){
							imageView.setImage(item.image);
						}
					});
					messageHorizontalBox.getChildren().add(imageView);
					messageHorizontalBox.getChildren().add(formattedMessage);
					messagesVerticalBox.getChildren().add(messageHorizontalBox);

			});



		}

	}






}
