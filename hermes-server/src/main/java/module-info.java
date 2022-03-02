module gov.iti.jets.server {

	requires transitive javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires com.zaxxer.hikari;
	requires mysql.connector.java;
	requires java.sql;
	requires java.rmi;
	requires transitive common.mod;
	requires java.management;
	requires javafx.swing;
	requires java.desktop;
	opens gov.iti.jets.server.presentation.gui.controllers to javafx.fxml;

	exports gov.iti.jets.server;
	exports gov.iti.jets.server.business.services.impl;

	provides common.business.services.Server with gov.iti.jets.server.business.services.impl.ServerImpl;

}
