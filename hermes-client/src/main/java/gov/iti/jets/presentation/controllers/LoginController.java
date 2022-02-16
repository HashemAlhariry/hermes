package gov.iti.jets.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import gov.iti.jets.business.dtos.UserDto;
import gov.iti.jets.business.services.*;
import gov.iti.jets.presentation.models.UserModel;
import gov.iti.jets.presentation.util.ModelsFactory;
import gov.iti.jets.presentation.util.StageCoordinator;

public class LoginController implements Initializable {

  @FXML
  private TextField emailTextField;

  @FXML
  private ImageView eyeImage;

  @FXML
  private TextField nameTextField;

  @FXML
  private PasswordField passwordTextField;

  @FXML
  private Button signInButton;

  private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
  private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;
  private Login loginSerice;

  private UserModel userModel = modelsFactory.getUserModel();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  
	

	try {
		//lookup("Login")  = Login same interface name.
		Registry registry = LocateRegistry.getRegistry();
		loginSerice = (Login) registry.lookup("Login");
	} catch (AccessException e) {
		e.printStackTrace();
	} catch (RemoteException e) {
		e.printStackTrace();
	} catch (NotBoundException e) {
		e.printStackTrace();
	}
	

    // nameTextField.textProperty().bindBidirectional(userModel.userNameProperty());
    // passwordTextField.textProperty().bindBidirectional(userModel.passwordProperty());

  }

  @FXML
  void eyeImageMouseClicked(MouseEvent event) {

  }

  @FXML
  void signUpHyperLinkAction(ActionEvent event) {
    stageCoordinator.switchToRegisterationScene();
  }

  @FXML
  void signinButtonAction(ActionEvent event) {
    stageCoordinator.switchtoHomePageScene();
  }

  @FXML
  void signinKeyPressed(KeyEvent event) {			
	  //If login authenticated -> login -> homepage
    String phoneNumber = userModel.getPhoneNumber();
    String password = userModel.getPassword();
    UserDto userDto = new UserDto(phoneNumber,password);

    try {
      if(loginSerice.isAuthenticated(userDto)){


      }
    } catch (RemoteException e) {
      //Alert to something gets wrong
      e.printStackTrace();
    }
	  
  }



}
