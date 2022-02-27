package gov.iti.jets.server.presentation.gui.controllers;

import common.business.dtos.InvitationResponseDto;
import common.business.dtos.PrivateGroupDetailsDto;
import gov.iti.jets.server.presentation.network.RMIConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.net.URL;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.iti.jets.server.presentation.gui.util.StageCoordinator;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class AnnouncementController implements Initializable {

	@FXML
	private Button sendMessageButton;
	@FXML
	private TextField messageTextField;
	@FXML
	private VBox announcementMessagesVBox;
	@FXML
	private ScrollPane mainScrollPane;
	@FXML
	private Button statisticsButton;

	@FXML
	private HTMLEditor htmlEditor;
	private String broadcastTextMessages;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		htmlEditor.setHtmlText("<body text='#ffffff' style='background-color:#2B233C'/>");
		broadcastTextMessages = "";
		sendMessageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {


				broadcastTextMessages=htmlEditor.getHtmlText();
				if (!broadcastTextMessages.isEmpty()) {
					showAlert();
				}

			}
		});

		statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StageCoordinator.INSTANCE.switchToStatisticsScene();
			}
		});

	}

	public void showAlert() {


		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Server Announcemenet");
		WebView webView = new WebView();
		webView.getEngine().loadContent(htmlEditor.getHtmlText());
		webView.setPrefSize(600, 200);
		alert.getDialogPane().setContent(webView);;
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {

			try {

				RMIConnection.INSTANCE.getServer().sendBroadCastToOnlineUsers(htmlEditor.getHtmlText());

			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("YES");

		}
	}

	@FXML
	public void stageMessages(ActionEvent e) {

		String messageToSend = messageTextField.getText();
		if (!messageToSend.isEmpty()) {
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER_LEFT);
			hBox.setPadding(new Insets(5, 5, 5, 10));
			Text text = new Text(messageToSend);

			broadcastTextMessages += messageToSend;
			broadcastTextMessages += " \n";

			TextFlow textFlow = new TextFlow(text);
			textFlow.setStyle("-fx-background-color:rgb(233,233,235); " + "-fx-background-radius: 20px;");
			textFlow.setPadding(new Insets(5, 10, 5, 10));
			hBox.getChildren().add(textFlow);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					announcementMessagesVBox.getChildren().add(hBox);
				}
			});

			messageTextField.clear();
		}
	}

	public static String getText(String htmlText) {

		String result = "";

		Pattern pattern = Pattern.compile("<[^>]*>");
		Matcher matcher = pattern.matcher(htmlText);
		final StringBuffer text = new StringBuffer(htmlText.length());

		while (matcher.find()) {
			matcher.appendReplacement(
					text,
					" ");
		}

		matcher.appendTail(text);

		result = text.toString().trim();

		return result;
	}

}
