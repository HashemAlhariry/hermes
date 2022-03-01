package gov.iti.jets.client.presentation.controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import common.business.dtos.InvitationSentDto;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.HTMLMessageParser;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presentation.util.Utils;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
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
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class HomePageController implements Initializable {

	Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

	@FXML
	private ImageView searchImageView;
	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private HTMLEditor messagHtmlEditor;

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

	private Color bgColor;
	private Color textColor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ImageView sendImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/button-send.png")));
		sendImageView.setFitHeight(20);
		sendImageView.setFitWidth(20);
		sendButton.setGraphic(sendImageView);

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

	private void sendMessageEventHandler() {

		String messageToSend = messagHtmlEditor.getHtmlText();

		ImageView imageView = new ImageView(contactImageView.getImage());
		imageView.setFitWidth(18);
		imageView.setFitHeight(18);

		HBox messageHorizontalBox = new HBox();
		messageHorizontalBox.setAlignment(Pos.CENTER_RIGHT);
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
		Date date = new Date();
		Timestamp timestamp2 = new Timestamp(date.getTime());
		var formattedMessage = HTMLMessageParser.INSTANCE.formatMessage(messageToSend, timestamp2);
		if (formattedMessage != null) {
			messageHorizontalBox.getChildren().add(formattedMessage);
			messageHorizontalBox.getChildren().add(imageView);
			messagesVerticalBox.getChildren().add(messageHorizontalBox);

			// SEND MESSAGE TO SPECIFIC USER As HTML

			messagHtmlEditor.setHtmlText("");
		}

	}

	@FXML
	void onSearchTextFieldClick(ActionEvent event) {
		//
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
		Platform.runLater(newContact::requestFocus);

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

}
