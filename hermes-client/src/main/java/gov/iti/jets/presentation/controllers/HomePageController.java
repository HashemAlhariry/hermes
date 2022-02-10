package gov.iti.jets.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HomePageController implements Initializable {

    Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

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
    private ImageView settingsImageView;
    @FXML
    private ImageView logoutImageView;
    @FXML
    private VBox messagesVerticalBox;
    @FXML
    private ImageView contactImageView;
    @FXML
    private ImageView chatImageView;

    
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

   

}
