package gov.iti.jets.client.presentation.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import net.synedra.validatorfx.Validator;
import net.synedra.validatorfx.Check.Context;
import common.business.dtos.UserDto;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.Util;
import gov.iti.jets.client.presentation.util.validation.Messages;
import gov.iti.jets.client.presentation.util.validation.Validators;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;

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
    private Button editButton;
	
	@FXML
	private TextField userNameTextField;

	@FXML
    private ComboBox<String> countryComboBox;

	@FXML
    private PasswordField newPasswordTextField;

    @FXML
    private PasswordField oldPasswordTextField;
    
	@FXML
    private Label oldPasswordLabel;

	@FXML
    private Label newPasswordLabel;

	@FXML
    private Button changePasswordButton;


	private boolean isNameBeingEdited;

	private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;

	private UserModel userModel = modelsFactory.getUserModel();
	private Validator validator = new Validator();
	private Validator validator2 = new Validator();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillCountryComboBox();
		countryComboBox.setPromptText(userModel.getCountry());
		binding();
		userNameTextField.setFocusTraversable(false);
		phoneTextField.setEditable(false);
		userNameTextField.setEditable(true);
		
		validator.createCheck()
                .dependsOn("username", userNameTextField.textProperty())
                .withMethod(this::validateUserName)
                .decorates(userNameTextField)
                .immediate();

        validator.createCheck()
                .dependsOn("email", emailTextField.textProperty())
                .withMethod(this::validateEmail)
                .decorates(emailTextField)
                .immediate();

        validator.createCheck()
                .dependsOn("dateOfBirth", birthdayDatePicker.valueProperty())
                .withMethod(this::validateDateOfBirth)
                .decorates(birthdayDatePicker)
                .immediate();

		validator.containsErrorsProperty().addListener((listener) -> {
            if (validator.containsErrors()) {
               editButton.setDisable(true);
            } else {
               editButton.setDisable(false);
            }
        });
		validator2.createCheck()
                .dependsOn("password", newPasswordTextField.textProperty())
                .withMethod(this::validatePassword)
                .decorates(newPasswordTextField)
                .immediate();

		newPasswordLabel.setVisible(false);
		oldPasswordLabel.setVisible(false);
		newPasswordTextField.setVisible(false);
		oldPasswordTextField.setVisible(false);

	}
	
	@FXML
    void changePasswordAction(MouseEvent event) {
		newPasswordLabel.setVisible(true);
		oldPasswordLabel.setVisible(true);
		newPasswordTextField.setVisible(true);
		oldPasswordTextField.setVisible(true);
    }
        
	//byte[]bytes = Util.INSTANCE.fromImageToArrayOfBytes(profilePictureImageView, "png");
	@FXML
	void changeProfileImage(MouseEvent event) {
		
		FileChooser chooser = new FileChooser();
		
		File newUserImage = chooser.showOpenDialog(stageCoordinator.getPrimaryStage());
		try {
			if (newUserImage != null) {
				profilePictureImageView.setImage(new Image(new FileInputStream(newUserImage)));
		
				UserDto userDto = new UserDto(userModel.getPhoneNumber(),userModel.getPassword(),userModel.getUserName(), userModel.getEmail() ,  userModel.getGender(), userModel.getDateOfBirth(),userModel.getCountry(),userModel.getBio());
				
				byte[]bytes = Util.INSTANCE.fromImageToArrayOfBytes(profilePictureImageView.getImage(),newUserImage.getName().split("\\.")[1]);
				if(bytes==null)return;
				boolean updatePicture = RMIConnection.INSTANCE.getServer().setUserProfilePicture(bytes, userDto , newUserImage.getName().split("\\.")[1]);
				
				if(updatePicture)
					System.out.println("Image is UPDATED");
				else
					System.out.println("Image is not updated");
				
			}
			else{
				System.out.println("You have not choose");
			}

		} catch (Exception e) {
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
	
	@FXML
    void editAction(MouseEvent event) {
		userNameTextField.setEditable(true);
		bioTextArea.setEditable(true);
		emailTextField.setEditable(true);
		if(oldPasswordTextField.equals(userModel.getPassword()))
			userModel.setPassword(newPasswordTextField.getText());	
		try {
			UserDto userDto = new UserDto(ModelsFactory.INSTANCE.getUserModel().getPhoneNumber()  
			,ModelsFactory.INSTANCE.getUserModel().getPassword() 
			,userNameTextField.getText()
			,emailTextField.getText()
			,ModelsFactory.INSTANCE.getUserModel().getGender()
			,birthdayDatePicker.getValue()
			,countryComboBox.getSelectionModel().getSelectedItem()
			,bioTextArea.getText());
			System.out.println("email :  "+userDto.email);
			UserDto userDto2 = RMIConnection.INSTANCE.getServer().updateUser(userDto);
			userModel = MapperImpl.INSTANCE.mapFromUserDto(userDto2);
			
			System.out.println("email222 :  "+userDto2.email);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Hermes");
				alert.setHeaderText(null);
				alert.setContentText("You have successfully updated your profile.");
				alert.showAndWait();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		newPasswordLabel.setVisible(false);
		oldPasswordLabel.setVisible(false);
		newPasswordTextField.setVisible(false);
		oldPasswordTextField.setVisible(false);
    }

	private void fillCountryComboBox() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Countries.txt");
        try {
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                countryComboBox.getItems().add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        countryComboBox.setPromptText(userModel.getCountry());
    }

	private void validateDateOfBirth(Context context) {
        if (Validators.INSTANCE.isDateOfBirthNotValid(birthdayDatePicker.valueProperty().get())) {
            context.error(Messages.INSTANCE.INVALID_DATE);
        }
    }

    private void validateUserName(Context context) {
        String userNameToCheck = context.get("username");
        if (userNameToCheck == null)
            return;
        if (userNameToCheck.isBlank()) {
            context.error(Messages.INSTANCE.USER_NAME_EMPTY);
        } else if (!Validators.INSTANCE.isUserNameValid(userNameToCheck))
            context.error(Messages.INSTANCE.INVALID_USER_NAME);
    }

    private void validateEmail(Context context) {
        String email = context.get("email");
        if (email == null)
            return;
        if (email.isBlank()) {
            context.error(Messages.INSTANCE.EMAIL_EMPTY);
        } else if (!Validators.INSTANCE.isEmailValidFormat(email)) {
            context.error(Messages.INSTANCE.INVALID_EMAIL_FORMAT);
        }
    }

	private void validatePassword(Context context) {
        String passwordToCheck = context.get("password");
        if (passwordToCheck == null || passwordToCheck.isBlank())
            context.error(Messages.INSTANCE.PASSWORD_EMPTY);
        else if (passwordToCheck.matches("[a-zA-Z]+"))
            context.error(Messages.INSTANCE.INVALID_PASSWORD_FORMAT);
        else if (passwordToCheck.matches("[0-9]+"))
            context.error(Messages.INSTANCE.INVALID_PASSWORD_FORMAT);
        else if (passwordToCheck.length() < 7)
            context.error(Messages.INSTANCE.PASSWORDS_MUST_MORETHAN_7);
    }

	private void setProfilePicture(String bytes){
		try {
			Image img = Util.INSTANCE.fromArrayOfBytesToImage(bytes.getBytes());
			profilePictureImageView.setImage(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void binding() {
		System.out.println("now user: " + userModel.getUserName());
		System.out.println("now phone: " + userModel.getPhoneNumber());
		System.out.println("bio: "+userModel.getBio());

		userNameTextField.textProperty().bindBidirectional(userModel.userNameProperty());
		phoneTextField.textProperty().bindBidirectional(userModel.phoneNumberProperty());
		
		emailTextField.textProperty().bindBidirectional(userModel.emailProperty());
		bioTextArea.textProperty().bindBidirectional(userModel.bioProperty());
		birthdayDatePicker.valueProperty().bindBidirectional(userModel.dateOfBirthProperty());
		genderGroup.selectToggle(userModel.getGender() ? maleRadioButton : femaleRadioButton);
		//userModel.countryProperty().bind(countryComboBox.getSelectionModel().getSelectedItem().toString().stringp);

		//Image = Util.INSTANCE.fromArrayOfBytesToImage(pro)
		profilePictureImageView.imageProperty().bindBidirectional(userModel.picturepProperty());
		userModel.countryProperty().bind(countryComboBox.getSelectionModel().selectedItemProperty());
	}
}
