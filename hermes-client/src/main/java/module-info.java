module gov.iti.jets {
	
	requires transitive javafx.controls;
	requires javafx.fxml;
	opens gov.iti.jets.presentation.controllers to javafx.fxml;

	exports gov.iti.jets;
}