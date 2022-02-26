package gov.iti.jets.client.presentation.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ProfileController implements Initializable {

	private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

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

	private boolean isNameBeingEdited;

	
	private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;

	private UserModel userModel = modelsFactory.getUserModel();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userNameTextField.setFocusTraversable(false);
		userNameTextField.setText(userModel.getUserName());
		phoneTextField.setText(userModel.getPhoneNumber());
		emailTextField.setText(userModel.getEmail());
		bioTextArea.setText(userModel.getBio());
		birthdayDatePicker.setValue(userModel.getDateOfBirth());
		genderGroup.setUserData(userModel.getGender());
		countryTextField.setText(userModel.getCountry());
		profilePictureImageView.setImage(userModel.getPicture());
		
	}

	@FXML
	void changeProfileImage(MouseEvent event) {
		FileChooser chooser = new FileChooser();
		File newUserImage = chooser.showOpenDialog(stageCoordinator.getPrimaryStage());
		try {
			if (newUserImage != null)
				profilePictureImageView.setImage(new Image(new FileInputStream(newUserImage)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void editUserNameTextField(MouseEvent event) {
		isNameBeingEdited = !isNameBeingEdited;
		if (isNameBeingEdited) {
			changeNameImageView.setImage(new Image(getClass().getResourceAsStream("/images/check.png")));
			userNameTextField.requestFocus();
		} else {
			changeNameImageView.setImage(new Image(getClass().getResourceAsStream("/images/writing.png")));
			stageCoordinator.getPrimaryStage().getScene().getRoot().requestFocus();
		}
		userNameTextField.editableProperty().set(isNameBeingEdited);
	}

	@FXML
	void backToPreviousScene(MouseEvent event) {
		stageCoordinator.switchtoHomePageScene();
	}
	public void binding(){
		userModel.phoneNumberProperty().bindBidirectional(phoneTextField.textProperty());
		userModel.emailProperty().bindBidirectional(emailTextField.textProperty());
		userModel.bioProperty().bindBidirectional(bioTextArea.textProperty());
		userModel.dateOfBirthProperty().bind(birthdayDatePicker.valueProperty());
		userModel.genderpProperty().bindBidirectional(genderGroup.getSelectedToggle().selectedProperty());
		userModel.countryProperty().bindBidirectional(countryTextField.textProperty());
		userModel.picturepProperty().bindBidirectional(profilePictureImageView.imageProperty());
	}
}
