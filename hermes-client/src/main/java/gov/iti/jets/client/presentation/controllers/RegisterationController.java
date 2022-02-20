package gov.iti.jets.client.presentation.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import net.synedra.validatorfx.Validator;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presentation.util.validation.Messages;
import gov.iti.jets.client.presentation.util.validation.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class RegisterationController implements Initializable {


    private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
    private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRMATION = "password_confirmation";

    @FXML
    private DatePicker birthDateFeild;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField confirmPasswordTextField;
    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TextField emailTextField;

    @FXML
    private ImageView eyeImage;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private ImageView wrongImage;


    Validator validator = new Validator();

   private void fillCountryComboBox()
   {
       InputStream inputStream = getClass().getClassLoader().getResourceAsStream("countries.txt");
      try{
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        while ((line=reader.readLine())!=null) {
                countryComboBox.getItems().add(line);
        }
    }
       catch(IOException ex){
           ex.printStackTrace();
       }
       countryComboBox.getSelectionModel().select("Egypt");
       countryComboBox.setPromptText("Country");
   }
    private void validatePassword(net.synedra.validatorfx.Check.Context context)
    {
       
        // String passwordToCheck = context.get(PASSWORD);  
        //     if(confirmPasswordTextField==passwordTextField)
        //     {
        //         context.error(Messages.PASSWORDS_MUST_MATCH);
        //     }
    }

    private void validateConfirmationPassword(net.synedra.validatorfx.Check.Context context){
        String confirmationPasswordToCheck = context.get(PASSWORD_CONFIRMATION);
        if(confirmationPasswordToCheck == null || confirmationPasswordToCheck.isBlank())
            context.error(Messages.PASSWORD_EMPTY);
        
        // else if(!Validators.INSTANCE.isContainCharacters(confirmationPasswordToCheck) )
        //     context.error(Messages.INVALID_PASSWORD_FORMAT);
    
        // else if(!Validators.INSTANCE.isContainCharacters(confirmationPasswordToCheck))
        //      context.error(Messages.INVALID_PASSWORD_FORMAT);
       
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserModel userModel = modelsFactory.getUserModel();
        ToggleGroup toggleGendGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(toggleGendGroup);
        femaleRadioButton.setToggleGroup(toggleGendGroup);
        fillCountryComboBox();

        validator.createCheck()
        .dependsOn(PASSWORD, passwordTextField.textProperty())
        .withMethod(this::validatePassword)
        .decorates(passwordTextField)
        .immediate(); 
        
        validator.createCheck()
        .dependsOn(PASSWORD_CONFIRMATION, confirmPasswordTextField.textProperty())
        .withMethod(this::validateConfirmationPassword)
        .decorates(confirmPasswordTextField)
        .immediate(); 
    }

    @FXML
    void eyeImageMouseClicked(MouseEvent event) {

    }

    @FXML
    void loginAction(MouseEvent event) {
        stageCoordinator.switchToLoginScene();
    }

    @FXML
    void registerationAction(ActionEvent event) {
        stageCoordinator.switchtoHomePageScene();
    }

    @FXML
    void registerationKeyPressed(KeyEvent event) {
        stageCoordinator.switchtoHomePageScene();
    }



}
