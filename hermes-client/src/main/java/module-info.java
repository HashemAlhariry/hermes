module gov.iti.jets.client {

	requires transitive javafx.controls;
	requires javafx.fxml;
	requires java.rmi;
	requires common.mod;
	
	opens gov.iti.jets.client.presentation.controllers to javafx.fxml;
	provides common.business.services.Client with  gov.iti.jets.client.business.services.ClientImpl;

	exports gov.iti.jets.client;
}