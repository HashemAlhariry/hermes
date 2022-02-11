package gov.iti.jets.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProfileController implements Initializable {

	@FXML
	private TextArea bioTextArea;

	@FXML
	private DatePicker birthdayDatePicker;

	@FXML
	private ImageView changeNameImageView;

	@FXML
	private ImageView changeProfilePictureImageView;

	@FXML
	private TextField countryTextField;

	@FXML
	private TextField emailTextField;

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

	}

	@FXML
	void changeProfileImage(MouseEvent event) {

	}

	@FXML
	void editUserNameTextField(MouseEvent event) {

	}

	@FXML
	void backToPreviousScene(MouseEvent event) {

	}

}
