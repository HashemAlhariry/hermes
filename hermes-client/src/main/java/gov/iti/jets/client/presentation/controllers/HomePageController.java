package gov.iti.jets.client.presentation.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import common.business.dtos.GroupDetailsDto;
import common.business.dtos.InvitationSentDto;
import common.business.dtos.MessageDto;
import common.business.util.OnlineStatus;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.presentation.models.MessageModel;
import gov.iti.jets.client.presentation.util.HTMLMessageParser;
import gov.iti.jets.client.presentation.util.ImageInfo;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presentation.util.Utils;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

public class HomePageController implements Initializable {

	Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

	private final FileChooser fileChooser = new FileChooser();

	@FXML
	private ImageView searchImageView;
	
	@FXML
	private BorderPane mainBorderPane;
	@FXML
	private HTMLEditor messagHtmlEditor;
	@FXML
	private VBox mainVertical;

	// @FXML
	// private TextField messageTextField;
	@FXML
	private ScrollPane sp_main;
	@FXML
	private Label currentGroupName;


	@FXML
	private Button sendButton;
	@FXML
	private ImageView profileImageView;
	@FXML
	private ImageView addContactView;
	@FXML
	private ImageView logoutImageView;
	@FXML
	private VBox messagesVerticalBox;
	@FXML
	private ListView<Contact> contactsListView;

	@FXML
	private Circle myOnlineStatus;

	@FXML
	private ComboBox<String> onlineStatusComboBox;
	@FXML
	private ImageView contactImageView;

	@FXML
	private ImageView addGroupView;

	private Color bgColor;
	private Color textColor;
	private int currentGroupId;

	private ObservableList<MessageModel> contentGroupMessages;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		messagesVerticalBox.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
				sp_main.setVvalue((Double) t1);
			}
		});

		contentGroupMessages = MessageModel.observableArrayList();
		ImageView sendImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/button-send.png")));
		sendImageView.setFitHeight(20);
		sendImageView.setFitWidth(20);
		sendButton.setGraphic(sendImageView);
		ObservableList<Contact> contacts = ModelsFactory.INSTANCE.getContactsModel().contactsProperty();
		ModelsFactory.INSTANCE.getUserModel().setPassword("");
		Platform.runLater(() -> {
			addAllContacts();
		});

		if (contactsListView.getItems().size() == 0) {
			currentGroupName.setVisible(false);
			contactImageView.setVisible(false);
		} else {
			currentGroupName.setVisible(true);
			contactImageView.setVisible(true);
		}

		contactsListView.setItems(contacts);
		contactsListView.setCellFactory(i -> new ContactListCell());
		contactsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			// getting group id to start chat with
			@Override
			public void handle(MouseEvent event) {
				contentGroupMessages.clear();
				messagesVerticalBox.getChildren().removeAll(messagesVerticalBox.getChildren());

				System.out.println(contactsListView.getSelectionModel().getSelectedItem().groupId);

				currentGroupId = contactsListView.getSelectionModel().getSelectedItem().groupId;

				Utils.INSTANCE.currentOpenGroupId = currentGroupId;

				Platform.runLater(() -> {
					currentGroupName.setVisible(true);
					contactImageView.setVisible(true);
					currentGroupName.setText(contactsListView.getSelectionModel().getSelectedItem().name);
					contactImageView.setImage(contactsListView.getSelectionModel().getSelectedItem().image);
				});
				// get all messages from database
				try {

					RMIConnection.INSTANCE.getServer().getAllMessagesByGroup(currentGroupId).forEach((message) -> {
						contentGroupMessages.add(new MessageModel(message.content, message.sendDate, message.groupId,
								message.senderPhone));
					});

					Platform.runLater(() -> {
						for (var message : contentGroupMessages) {
							System.out.println(message.content);
							boolean checker = false;
							if (message.senderPhone.equals(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber()))
								checker = true;
							createMessage(message.content, new Timestamp(message.sendDate.getTime()), checker);
						}
					});
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				// update it when user click
			}
		});

		Platform.runLater(() -> {
			initUserStatus();
		});

		// // Sending message to vbox in chat box to a specific contact
		// sendButton.setOnAction(new EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent event) {
		// String messageToSend = messageTextField.getText();
		// if (!messageToSend.isEmpty()) {

		messagHtmlEditor.lookup(".top-toolbar").setManaged(false);
		messagHtmlEditor.lookup(".top-toolbar").setVisible(false);

		ColorPicker backGroundColorPicker = new ColorPicker();
		ColorPicker textColorPicker = new ColorPicker();

		backGroundColorPicker.setOnAction(event -> {
			bgColor = backGroundColorPicker.getValue();
		});
		backGroundColorPicker.setTooltip(new Tooltip("Background"));
		backGroundColorPicker.getStyleClass().add("bg-color-pricker");

		textColorPicker.setTooltip(new Tooltip("textColor"));
		textColorPicker.getStyleClass().add("color-pricker");
		textColorPicker.setOnAction(event -> textColor = textColorPicker.getValue());

		ToolBar customToolBar = (ToolBar) messagHtmlEditor.lookup(".bottom-toolbar");
		customToolBar.getItems().add(backGroundColorPicker);
		customToolBar.getItems().add(textColorPicker);

		messagHtmlEditor.requestFocus();

	}

	@FXML
	void sendMessageAction(ActionEvent event) {
		sendMessageEventHandler();
	}

	@FXML
	void sendMessageByEnterAction(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			sendMessageEventHandler();
			// messagHtmlEditor.requestFocus();
		}
	}

	public VBox getMessagesVerticalBox() {
		return messagesVerticalBox;
	}

	public void setMessagesVerticalBox(VBox messagesVerticalBox) {
		this.messagesVerticalBox = messagesVerticalBox;
	}

	private void sendMessageEventHandler() {
		String messageToSend = messagHtmlEditor.getHtmlText();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		String messageToSendTo = createMessage(messageToSend, timestamp, true);

		// get sender photo phone to put it beside the message (PRIVATE _ GROUP)

		System.out.println(messageToSendTo);
		// send message to save it to database
		if (messageToSendTo != null) {
			try {
				RMIConnection.INSTANCE.getServer().sendMessage(new MessageDto(
						messageToSendTo,
						new java.sql.Date(date.getTime()),
						currentGroupId,
						ModelsFactory.INSTANCE.getUserModel().getPhoneNumber()));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}

	private String createMessage(String messageToSend, Timestamp timestamp, boolean check) {

		ImageView imageView = new ImageView(contactImageView.getImage());
		imageView.setFitWidth(18);
		imageView.setFitHeight(18);
		HBox messageHorizontalBox = new HBox();

		if (check)
			messageHorizontalBox.setAlignment(Pos.CENTER_RIGHT);
		else
			messageHorizontalBox.setAlignment(Pos.CENTER_LEFT);

		messageHorizontalBox.setPadding(new Insets(5, 5, 5, 10));
		// add text and backgroud colors as css in the html message
		// insert style at paragraph tag
		// add bgcolor and text color in this style
		messageToSend = Util.INSTANCE.insertString(messageToSend, " style=\"\"", messageToSend.indexOf("p"));
		if (textColor != null)
			messageToSend = Util.INSTANCE.insertString(messageToSend, " color: " + textColor + "; ",
					messageToSend.indexOf("style=\"") + "style=\"".length() - 1);

		if (bgColor != null)
			messageToSend = Util.INSTANCE.insertString(messageToSend, " background-color: " + bgColor + "; ",
					messageToSend.indexOf("style=\"") + "style=\"".length() - 1);

		var formattedMessage = HTMLMessageParser.INSTANCE.formatMessage(messageToSend, timestamp);
		if (formattedMessage != null) {
			if (check) {
				messageHorizontalBox.getChildren().add(formattedMessage);
				messageHorizontalBox.getChildren().add(imageView);
			} else {
				messageHorizontalBox.getChildren().add(imageView);
				messageHorizontalBox.getChildren().add(formattedMessage);
			}

			messagesVerticalBox.getChildren().add(messageHorizontalBox);
			// SEND MESSAGE TO SPECIFIC USER As HTML
			messagHtmlEditor.setHtmlText("");
			return messageToSend;
		}
		return null;
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

	public static void appendMessage(MessageDto messageDto) {
		// createMessage(messageDto.content,messageDto.sendDate,false);
	}

	@FXML
	void onProfileClicked(MouseEvent mouseEvent) {
		StageCoordinator.INSTANCE.switchToProfileScene();
	}


	@FXML
	void onContactClicked(MouseEvent mouseEvent) {
		// stageCoordinator.switchToContactScene();
	}

	@FXML
	void onLogoutClicked(MouseEvent mouseEvent) {
		try {

			RMIConnection.INSTANCE.close(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
			ModelsFactory.INSTANCE.getContactsModel().contactsProperty().clear();
			StageCoordinator.INSTANCE.getSceneMap().clear();
			clearCredsIfFound();
			StageCoordinator.INSTANCE.switchToLoginScene();
		} catch (RemoteException | NotBoundException e) {
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
		Platform.runLater(newContact::requestFocus);
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return invitedContacts.size();
			}
			return null;
		});
		Optional<Integer> result = dialog.showAndWait();
		result.ifPresent(System.out::println);
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


		ImageInfo imageInfo = new ImageInfo();

		Platform.runLater(() -> chooseImageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					ImageInfo imageInfo2=loadPicture(defaultImageView);
					imageInfo.extension=imageInfo2.extension;
					imageInfo.bytes=imageInfo2.bytes;
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

			String imageName = 	String.valueOf(myObj.getNano());
			System.out.println(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber());
			try {
				RMIConnection.INSTANCE.getServer().addGroupChat(
						new GroupDetailsDto(
								groupNameField.getText(),
								groupNameField.getText(),
								ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(),
								invitedContacts,
								imageInfo.bytes,
								groupNameField.getText() + "_" + imageName+"."+imageInfo.extension));
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

	private ImageInfo loadPicture(ImageView imageView) throws IOException {
		ImageInfo imageInfo;
		byte[] imageBytes;
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {

			imageView.setImage(new Image(file.toURI().toString()));
			imageBytes = Files.readAllBytes(file.toPath());
			return new ImageInfo(imageBytes,file.getName().split("\\.")[1]);

		} else {
			System.out.println("InvalidImage");
		}
		return null;
	}

	private void clearCredsIfFound() {
		File creds = Paths.get("creds").toFile();
		if (!creds.exists())
			return;
		creds.delete();
	}

	@FXML
	void onSearchTextFieldClick(ActionEvent actionEvent){

	}

}
