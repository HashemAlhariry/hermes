module gov.iti.jets {

	requires transitive javafx.controls;
	requires javafx.fxml;
	requires com.zaxxer.hikari;
	requires mysql.connector.java;
	requires java.sql;

	opens gov.iti.jets.presentation.controllers to javafx.fxml;

	exports gov.iti.jets;
}