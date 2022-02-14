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
import java.util.ResourceBundle;
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

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    UserModel userModel = modelsFactory.getUserModel();
    // nameTextField.textProperty().bindBidirectional(userModel.userNameProperty());
    // passwordTextField.textProperty().bindBidirectional(userModel.passwordProperty());

  }

}
