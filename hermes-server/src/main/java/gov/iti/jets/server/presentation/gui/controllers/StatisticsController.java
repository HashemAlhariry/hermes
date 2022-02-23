package gov.iti.jets.server.presentation.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import gov.iti.jets.server.presentation.gui.util.StageCoordinator;

public class StatisticsController implements Initializable {

	@FXML
	private PieChart onlineOfflineUserPieChart;

	@FXML
	private Label textLabel;

	@FXML
	private PieChart countryPieChart;
	@FXML
	private PieChart userGenderPieChart;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<PieChart.Data> pieChartDataForGender = FXCollections.observableArrayList(
			new PieChart.Data("Males", 13),
			new PieChart.Data("Females", 25));


		ObservableList<PieChart.Data> pieChartDataForOnlineOffline = FXCollections.observableArrayList(
			new PieChart.Data("Online", 40),
			new PieChart.Data("Offline", 100));

		ObservableList<PieChart.Data> pieChartDataForCountry = FXCollections.observableArrayList(
			new PieChart.Data("Egypt", 20),
			new PieChart.Data("USA", 50));

		userGenderPieChart.setData(pieChartDataForGender);
		onlineOfflineUserPieChart.setData(pieChartDataForOnlineOffline);
		countryPieChart.setData(pieChartDataForCountry);
	}


	@FXML
	void backToPreviousScene(MouseEvent event) {
		StageCoordinator.INSTANCE.switchToAnnouncementScene();
	}
}
