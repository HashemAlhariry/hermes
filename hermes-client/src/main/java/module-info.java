module gov.iti.jets.client {

	requires transitive javafx.controls;
	requires javafx.fxml;
	requires java.rmi;
	requires common.mod;
	
	opens gov.iti.jets.client.presentation.controllers to javafx.fxml;

	exports gov.iti.jets.client;
}