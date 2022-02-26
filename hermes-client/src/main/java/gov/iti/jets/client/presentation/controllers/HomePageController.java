package gov.iti.jets.client.presentation.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HomePageController implements Initializable {

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

	@FXML
	private ImageView addContactView;

	@FXML
	private ImageView contactImageView;

	@FXML
	private ImageView logoutImageView;

	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private VBox mainVertical;

	@FXML
	private TextField messageTextField;

	@FXML
	private VBox messagesVerticalBox;

	@FXML
	private ImageView optionsOnChat;

	@FXML
	private ImageView profileImageView;

	@FXML
	private ImageView searchImageView;

	@FXML
	private TextField searchTextField;

	@FXML
	private Button sendButton;

	@FXML
	private ScrollPane sp_main;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Sending message to vbox in chat box to a specific contact
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String messageToSend = messageTextField.getText();
				if (!messageToSend.isEmpty()) {

					ImageView imageView = new ImageView(contactImageView.getImage());
					imageView.setFitWidth(18);
					imageView.setFitHeight(18);

					HBox messageHorizontalBox = new HBox();
					messageHorizontalBox.setAlignment(Pos.CENTER_RIGHT);
					messageHorizontalBox.setPadding(new Insets(5, 5, 5, 10));

					Text textMessage = new Text(messageToSend);
					TextFlow messageTextFlow = new TextFlow(textMessage);
					messageTextFlow.setStyle("-fx-color: rgb(255,255,255); " + "-fx-background-color:  #685490; "
							+ " -fx-background-radius: 20px; ");
					messageTextFlow.setPadding(new Insets(5, 10, 5, 10));
					textMessage.setFill(Color.color(0.934, 0.945, 0.996));

					messageHorizontalBox.getChildren().add(messageTextFlow);
					messageHorizontalBox.getChildren().add(imageView);
					messagesVerticalBox.getChildren().add(messageHorizontalBox);

					// SEND MESSAGE TO SPECIFIC USER

					messageTextField.clear();

				}
			}
		});

		// Platform.runLater(() -> {
		// 	initUserImage();
		// });

	}

	private void initUserImage() {
		Image image;

		try {
			byte[] imgBytes = RMIConnection.INSTANCE.getServer()
					.getUserImageByPhone(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
			image = Util.INSTANCE.fromArrayOfBytesToImage(imgBytes);
			ModelsFactory.INSTANCE.getUserModel().setPicture(image);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void onSearchTextFieldClick(ActionEvent event) {

	}

	@FXML
	void onProfileClicked(MouseEvent mouseEvent) {
		stageCoordinator.switchToProfileScene();
	}

	@FXML
	void onLogoutClicked(MouseEvent mouseEvent) {
		try {
			RMIConnection.INSTANCE.close();
			// ModelsFactory.INSTANCE.setUserModel(new UserModel());
			StageCoordinator.INSTANCE.getSceneMap().clear();
			stageCoordinator.switchToLoginScene();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onAddContactClicked(MouseEvent event) {

	}

	@FXML
	void onContactClicked(MouseEvent event) {

	}

	@FXML
	void onSearchContactList(MouseEvent event) {

	}

}
