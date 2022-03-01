package gov.iti.jets.client.presentation.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import common.business.dtos.GroupDetailsDto;
import common.business.dtos.InvitationSentDto;
import common.business.util.OnlineStatus;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presentation.util.Utils;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

public class HomePageController implements Initializable {

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

	private final FileChooser fileChooser = new FileChooser();

	@FXML
	private ImageView addContactView;

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

	@FXML
	private Circle myOnlineStatus;

	@FXML
	private ComboBox<String> onlineStatusComboBox;
	private ImageView contactImageView;
	@FXML
	private ImageView addGroupView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<Contact> contacts = ModelsFactory.INSTANCE.getContactsModel().contactsProperty();
		ModelsFactory.INSTANCE.getUserModel().setPassword("");
		Platform.runLater(() -> {
			addAllContacts();
		});
		contactsListView.setItems(contacts);
		contactsListView.setCellFactory(i -> new ContactListCell());

		Platform.runLater(() -> {
			initUserStatus();
		});

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

	private void addAllContacts() {
		try {
			var fetchedContacts = RMIConnection.INSTANCE
					.getServer()
					.getAllGroupsByUser(MapperImpl.INSTANCE.mapToUserDto(ModelsFactory.INSTANCE.getUserModel()))
					.stream()
					.map((gdto) -> {
						try {
							return new Contact(gdto.id, gdto.name,
									Util.INSTANCE.fromArrayOfBytesToImage(gdto.image), gdto.status);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return new Contact(gdto.id, gdto.name, null, gdto.status);
					}).collect(Collectors.toList());
			ModelsFactory.INSTANCE.getContactsModel().setContacts(fetchedContacts);
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
			ModelsFactory.INSTANCE.getContactsModel().contactsProperty().clear();
			StageCoordinator.INSTANCE.getSceneMap().clear();
			clearCredsIfFound();
			stageCoordinator.switchToLoginScene();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	// Added Contact/Contacts feature
	@FXML
	void onAddContactClicked(MouseEvent mouseEvent) {

		// contact list to send invitation to all users
		List<String> invitedContacts = new ArrayList<>();

		Dialog<Integer> dialog = new Dialog<>();
		dialog.setTitle("Enter Phone Number");

		ButtonType loginButtonType = new ButtonType("Add Contacts", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		GridPane gridPane = new GridPane();
		Button addOneContact = new Button("Add contact");
		TextField newContact = new TextField();
		newContact.setPromptText("Add Numbers");
		TextArea addedContacts = new TextArea();
		addedContacts.setPrefHeight(100);
		addedContacts.setPrefWidth(200);
		addedContacts.setEditable(false);
		addedContacts.setPromptText("Added Contacts");

		gridPane.setStyle("-fx-background-color: #363A54; "
				+ "-fx-font-style: italic;");
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		gridPane.setPadding(new Insets(20, 150, 10, 10));

		gridPane.add(newContact, 0, 0);
		gridPane.add(addOneContact, 0, 1);
		gridPane.add(addedContacts, 4, 0);

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
	void onAddGroupClicked(MouseEvent mouseEvent) {

		// contact list to send invitation group to all users
		List<String> invitedContacts = new ArrayList<>();

		Dialog<Integer> dialog = new Dialog<>();
		dialog.setTitle("Create Group");

		ButtonType loginButtonType = new ButtonType("Create Group", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		GridPane gridPane = new GridPane();
		Button addOneContact = new Button("Add contact");

		TextField newContact = new TextField();
		newContact.setPromptText("Add Numbers");

		TextArea addedContacts = new TextArea();
		addedContacts.setEditable(false);
		addedContacts.setPromptText("Added Contacts");
		addedContacts.setPrefHeight(100);
		addedContacts.setPrefWidth(150);

		gridPane.setStyle("-fx-background-color: #363A54; "
				+ "-fx-font-style: italic;");
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		gridPane.setPadding(new Insets(20, 150, 10, 10));

		ImageView defaultImageView = new ImageView();
		defaultImageView.setFitHeight(100);
		defaultImageView.setFitWidth(100);
		Button chooseImageButton = new Button("Choose Image");
		ImageView temp = new ImageView();
		temp.setFitHeight(100);
		temp.setFitWidth(120);
		gridPane.add(newContact, 0, 1);
		gridPane.add(addOneContact, 0, 2);

		Label groupNameLabel = new Label("Group Name");
		groupNameLabel.setTextFill(Color.WHITE);
		gridPane.add(groupNameLabel, 1, 0);

		TextField groupNameField = new TextField();
		groupNameField.setPromptText("Group Name");
		gridPane.add(groupNameField, 1, 1);
		gridPane.add(addedContacts, 1, 2);
		gridPane.add(temp, 2, 1);
		gridPane.add(defaultImageView, 3, 1);
		gridPane.add(chooseImageButton, 3, 2);

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

		// Image bytes
		final byte[][] imageBytes = new byte[1][1];
		Platform.runLater(() -> chooseImageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					imageBytes[0] = loadPicture(defaultImageView);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));

		dialog.getDialogPane().setContent(gridPane);
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

		if (!invitedContacts.isEmpty() && !groupNameField.getText().isEmpty()) {
			LocalDateTime myObj = LocalDateTime.now();
			// SEND GROUP DETAILS TO BE SAVED TO SERVER
			System.out.println(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
			try {
				RMIConnection.INSTANCE.getServer().addGroupChat(
						new GroupDetailsDto(
								groupNameField.getText(),
								groupNameField.getText() + "_" + myObj,
								ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(),
								invitedContacts,
								imageBytes[0],
								groupNameField.getText() + "_" + myObj));
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}
	}

	@FXML
	void onSearchContactList(MouseEvent event) {

	}

	private void initUserStatus() {
		onlineStatusComboBox.getItems().add("Available");
		onlineStatusComboBox.getItems().add("Away");
		onlineStatusComboBox.getItems().add("Busy");
		// the default color on login
		myOnlineStatus.setFill(Color.rgb(55, 255, 3));
		// the default status on login
		ModelsFactory.INSTANCE.getUserModel().setOnlineStatus(OnlineStatus.AVAILABLE);

		onlineStatusComboBox.setOnAction((event) -> {
			int selectedIndex = onlineStatusComboBox.getSelectionModel().getSelectedIndex();
			try {
				switch (selectedIndex) {
					case 0:
						RMIConnection.INSTANCE.getServer().changeMyOnlineStatus(OnlineStatus.AVAILABLE,
								ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
						myOnlineStatus.setFill(Color.rgb(55, 255, 3));
						ModelsFactory.INSTANCE.getUserModel().setOnlineStatus(OnlineStatus.AVAILABLE);
						break;
					case 1:
						RMIConnection.INSTANCE.getServer().changeMyOnlineStatus(OnlineStatus.AWAY,
								ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
						myOnlineStatus.setFill(Color.rgb(255, 182, 3));
						ModelsFactory.INSTANCE.getUserModel().setOnlineStatus(OnlineStatus.AWAY);
						break;
					case 2:
						RMIConnection.INSTANCE.getServer().changeMyOnlineStatus(OnlineStatus.BUSY,
								ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
						myOnlineStatus.setFill(Color.rgb(255, 20, 20));
						ModelsFactory.INSTANCE.getUserModel().setOnlineStatus(OnlineStatus.BUSY);
						break;
					default:
						break;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
		onlineStatusComboBox.getSelectionModel().select(0);

	}

	private byte[] loadPicture(ImageView imageView) throws IOException {

		byte[] imageBytes;
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {

			imageView.setImage(new Image(file.toURI().toString()));
			imageBytes = Files.readAllBytes(file.toPath());
			return imageBytes;

		} else {
			System.out.println("InvalidImage");
		}
		return null;
	}

	private void clearCredsIfFound() {
		File creds = Paths.get("creds").toFile();
		if (!creds.exists())
			return;
		try {
			Files.readAllLines(creds.toPath()).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		creds.delete();
	}

}
