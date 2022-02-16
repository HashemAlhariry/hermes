module gov.iti.jets {

	requires transitive javafx.controls;
	requires javafx.fxml;
	requires java.rmi;
	
	opens gov.iti.jets.presentation.controllers to javafx.fxml;

	exports gov.iti.jets;
}