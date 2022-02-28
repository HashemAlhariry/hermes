package gov.iti.jets.server.presentation.gui.controllers;


import gov.iti.jets.server.presentation.gui.util.StatisticsData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import gov.iti.jets.server.presentation.gui.util.StageCoordinator;

public class StatisticsController implements Initializable {


	@FXML
	private PieChart onlineOfflineUserPieChart;
	@FXML
	private PieChart countryPieChart;
	@FXML
	private PieChart userGenderPieChart;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userGenderPieChart.setData(StatisticsData.INSTANCE.pieChartDataForGender);
		onlineOfflineUserPieChart.setData(StatisticsData.INSTANCE.pieChartDataForOnlineOffline);
		countryPieChart.setData(StatisticsData.INSTANCE.pieChartDataForCountry);

	}




	@FXML
	void backToPreviousScene(MouseEvent event) {
		StageCoordinator.INSTANCE.switchToAnnouncementScene();
	}


}
