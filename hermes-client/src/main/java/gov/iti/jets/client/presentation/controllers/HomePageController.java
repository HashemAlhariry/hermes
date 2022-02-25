package gov.iti.jets.client.presentation.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import common.business.dtos.InvitationSentDto;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.Utils;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
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
import javafx.stage.FileChooser;

public class HomePageController implements Initializable {

	Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

	private final FileChooser fileChooser = new FileChooser();

	@FXML
	private ImageView searchImageView;
	@FXML
	private BorderPane mainBorderPane;
	@FXML
	private TextField messageTextField;
	@FXML
	private TextField searchTextField;
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
	private ImageView contactImageView;
	@FXML
	private ImageView addGroupView;

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

	}

	@FXML
	void onSearchTextFieldClick(ActionEvent event) {

	}

	@FXML
	void onSearchContactList(MouseEvent mouseEvent) {
		System.out.println("CLICKED");
	}

	@FXML
	void onProfileClicked(MouseEvent mouseEvent) {
		UserModel userModel1 = ModelsFactory.INSTANCE.getUserModel();
		userModel1.setEmail("hashemalhariry33@gmail.com");
		userModel1.setPhoneNumber("01149056691");
		userModel1.setUserName("HASHEM");

		// RMIConnection.INSTANCE.getConnectedClients().sendMessage(message);
		// stageCoordinator.switchToProfileScene();

	}

	@FXML
	void onContactClicked(MouseEvent mouseEvent) {
		// stageCoordinator.switchToContactScene();
	}

	@FXML
	void onLogoutClicked(MouseEvent mouseEvent) {
		UserModel userModel1 = ModelsFactory.INSTANCE.getUserModel();
		userModel1.setEmail("Mina@gmail.com");
		userModel1.setPhoneNumber("01285097233");
		userModel1.setUserName("MINA");
		// stageCoordinator.switchToLoginScene();
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
		addedContacts.setPrefWidth(100);
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

				RMIConnection.INSTANCE.getServer().sendInvitation(new InvitationSentDto(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber(), invitedContacts));

			} catch (RemoteException e) {

				e.printStackTrace();
			}
		}

	}

	@FXML
	void onAddGroupClicked(MouseEvent mouseEvent){

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
		gridPane.add(groupNameLabel,1 , 0);

		TextField groupNameField = new TextField();
		groupNameField.setPromptText("Group Name");
		gridPane.add(groupNameField,1 , 1);
		gridPane.add(addedContacts, 1, 2);
		gridPane.add(temp,2 , 1);
		gridPane.add(defaultImageView,3 , 1);
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

		//Image bytes
		final byte[][] imageBytes = new byte[1][1];
		Platform.runLater(() -> chooseImageButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					imageBytes[0] =loadPicture(defaultImageView);
					//System.out.println(imageBytes[0]);
					/*
					try (FileOutputStream fos = new FileOutputStream("pathname")) {
						fos.write(imageBytes[0]);
						//fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
					}
					*/
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

			//SEND GROUP DETAILS TO BE SAVED TO SERVER
			System.out.println("ALL THINGS GOOD");

		}

	}


	private byte[] loadPicture(ImageView imageView) throws IOException {

		byte [] imageBytes;
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif","*.jpeg"));
		File file =  fileChooser.showOpenDialog(null);

		if(file!=null){

			imageView.setImage(new Image(file.toURI().toString()));
			imageBytes=Files.readAllBytes(file.toPath());
			return imageBytes;

		}else{
			System.out.println("InvalidImage");
		}
	return null;
	}


}
