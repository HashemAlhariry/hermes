package gov.iti.jets.client.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ContactController implements Initializable {

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	@FXML
	private Button addToContactButton;

	@FXML
	private TextArea bioTextArea;

	@FXML
	private TextField birthDayTextField;

	@FXML
	private TextField countryTextField;

	@FXML
	private RadioButton femaleRadioButton;

	@FXML
	private ToggleGroup genderGroup;

	@FXML
	private RadioButton maleRadioButton;

	@FXML
	private TextField phoneTextField;

	@FXML
	private ImageView previousImageView;

	@FXML
	private ImageView profilePictureImageView;

	@FXML
	private TextField userNameTextField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userNameTextField.setFocusTraversable(false);
	}


	@FXML
	void addContact(ActionEvent event){

	}

	@FXML
	void backToPreviousScene(MouseEvent event) {
		stageCoordinator.switchtoHomePageScene();
	}
	
}
