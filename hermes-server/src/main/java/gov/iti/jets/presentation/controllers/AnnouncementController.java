package gov.iti.jets.presentation.controllers;

import gov.iti.jets.presentation.util.StageCoordinator;
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


import java.util.ResourceBundle;

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

    private String broadcastTextMessages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        broadcastTextMessages="";

        announcementMessagesVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainScrollPane.setVvalue((Double) t1);
            }
        });

        sendMessageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!broadcastTextMessages.isEmpty())
                       showAlert();
            }
        });


        statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StageCoordinator.getInstance().switchToStatisticsScene();
            }
        });
    }


    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SERVER ANNOUNCEMENT");
        alert.setHeaderText("Broadcast");
        alert.setContentText(broadcastTextMessages);
        alert.showAndWait();
    }

    @FXML
    public void stageMessages(ActionEvent e) {
        String messageToSend = messageTextField.getText();
        if(!messageToSend.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            Text text = new Text(messageToSend);

            broadcastTextMessages+=messageToSend;
            broadcastTextMessages+=" ";

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



}
