package gov.iti.jets.client.presentation.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import common.business.dtos.InvitationSentDto;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presentation.util.Utils;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
	private ListView<Contact> contactsListView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<Contact> contacts = FXCollections.observableArrayList();
		// this line is only for testing puroses till hashing password is handeled
		// only works for user mina
		ModelsFactory.INSTANCE.getUserModel().setPassword("123");
		Platform.runLater(() -> {
			addAllContacts(contacts);
		});
		contactsListView.setItems(contacts);
		contactsListView.setCellFactory(i -> new ContactListCell());

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
		// initUserImage();
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

	private void addAllContacts(ObservableList<Contact> contacts) {
		try {
			var fetchedContacts = RMIConnection.INSTANCE
					.getServer()
					.getAllGroupsByUser(MapperImpl.INSTANCE.mapToUserDto(ModelsFactory.INSTANCE.getUserModel()))
					.stream()
					.map((gdto) -> {
						try {
							return new Contact(gdto.id, gdto.name,
									Util.INSTANCE.fromArrayOfBytesToImage(gdto.image));
						} catch (IOException e) {
							e.printStackTrace();
						}
						return new Contact(gdto.id, gdto.name, null);
					}).collect(Collectors.toList());
			contacts.addAll(fetchedContacts);
		} catch (RemoteException e) {
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
	void onContactClicked(MouseEvent mouseEvent) {
		// stageCoordinator.switchToContactScene();
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
	void onAddContactClicked(MouseEvent mouseEvent) {

		// contact list to send invitation to all users
		List<String> invitedContacts = new ArrayList<String>();

		Dialog<Integer> dialog = new Dialog<>();
		dialog.setTitle("Enter Phone Number");

		ButtonType loginButtonType = new ButtonType("Add Contact/Contacts", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color: #363A54; "
				+ "-fx-font-style: italic;");
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		Button addOneContact = new Button("Add contact");
		TextField newContact = new TextField();
		newContact.setPromptText("Add Numbers");
		TextArea addedContacts = new TextArea();
		addedContacts.setEditable(false);
		addedContacts.setPromptText("Added Contacts");
		gridPane.add(newContact, 0, 0);
		gridPane.add(addOneContact, 0, 1);
		gridPane.add(addedContacts, 6, 0);

		dialog.getDialogPane().setContent(gridPane);

		Platform.runLater(() -> addOneContact.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!newContact.getText().isEmpty()
						&& Utils.INSTANCE.checkNumberInString(newContact.getText().trim())) {
					invitedContacts.add(newContact.getText().trim());
					addedContacts.appendText(newContact.getText() + " \n");
					newContact.clear();
				}

			}
		}));

		// Request focus on the newContact field by default.
		Platform.runLater(() -> newContact.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return invitedContacts.size();
			}
			return null;
		});

		Optional<Integer> result = dialog.showAndWait();
		result.ifPresent(length -> {
			System.out.println(length);
		});
		if (!invitedContacts.isEmpty()) {
			// calling RMI SERVICE TO ADD CONTACTS if list is not empty
			// SEND USER PHONE AND LIST OF ADDED CONTACT BY USER
			try {

				System.out.println("Client " + ModelsFactory.INSTANCE.getUserModel().getPhoneNumber()
						+ " Sending all invitation ...");

				RMIConnection.INSTANCE.getServer().sendInvitation(
						new InvitationSentDto(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(), invitedContacts));

			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void onSearchContactList(MouseEvent event) {

	}

}
