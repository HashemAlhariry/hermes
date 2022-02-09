package gov.iti.jets.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.chart.PieChart;

public class HomePageController implements Initializable {

    Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TextField tf_message;

    @FXML
    private Button button_send;

    @FXML
    private ImageView chattingScreen;

    @FXML
    private ImageView logout;

    @FXML
    private ImageView optionsOnChat;

    @FXML
    private ImageView profilePicture;

    @FXML
    private ImageView settings;

    @FXML
    private VBox vbox_messages;

    @FXML
    private ScrollPane sp_main;

    @FXML
    private Label labeltext;
    @FXML
    private Label labeltext2;

    @FXML
    private VBox mainVertical;

    @FXML
    void onSearchTextFieldClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = tf_message.getText();
                if (!messageToSend.isEmpty()) {

                    HBox hBox = new HBox();

                    System.out.print("ENTERED" + messageToSend);
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                            "-fx-background-color: rgb(15,125,242); " +
                            " -fx-background-radius: 20px; ");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));
                    hBox.getChildren().add(textFlow);

                    vbox_messages.getChildren().add(hBox);

                    // SEND MESSAGE TO SPECIFIC USER

                    tf_message.clear();

                }
            }
        });
    }

}
