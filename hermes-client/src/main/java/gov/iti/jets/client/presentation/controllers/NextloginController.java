package gov.iti.jets.client.presentation.controllers;

import java.net.URL;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Mapper;
import gov.iti.jets.client.business.services.impl.MapperImpl;
import gov.iti.jets.client.business.services.util.ServiceFactory;
import gov.iti.jets.client.presentation.models.UserModel;
import gov.iti.jets.client.presentation.util.ModelsFactory;
import gov.iti.jets.client.presentation.util.StageCoordinator;
import gov.iti.jets.client.presistance.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;

public class NextloginController implements Initializable {
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signInButton;

    private final StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
	private final ModelsFactory modelsFactory = ModelsFactory.INSTANCE;

	private UserModel userModel = modelsFactory.getUserModel();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        getFormsValues();
        
		try {
                Registry registry = LocateRegistry.getRegistry();
                for (var s : registry.list()) {
                    System.out.println(s);
			}

		} catch (AccessException e) {
			e.printStackTrace();
            
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
  
    @FXML
    void signinButtonAction(ActionEvent event) {
            try {
                UserAuthDto userAuthDto = MapperImpl.INSTANCE.mapToUserAuthDto(userModel);
                UserDto userDto = RMIConnection.INSTANCE.getServer().login(ServiceFactory.INSTANCE.getClientImpl(), userAuthDto);
                if(userDto!=null){
                    stageCoordinator.switchtoHomePageScene();
               }
               else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hermes");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Password");
                alert.showAndWait();
               }
               
            } catch (RemoteException e) {
    
                e.printStackTrace();
            }
        }

    @FXML
    void signinKeyPressed(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER){
			stageCoordinator.switchToLoginScene();
		}
    }
   private void getFormsValues(){
       userModel.passwordProperty().bindBidirectional(passwordTextField.textProperty());
    }

}
