package gov.iti.jets.server;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.daos.impl.UserDaoImpl;
import gov.iti.jets.server.persistance.util.DaosFactory;
import gov.iti.jets.server.presentation.gui.util.StageCoordinator;
import gov.iti.jets.server.presentation.gui.util.StatisticsData;
import gov.iti.jets.server.presentation.network.RMIConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
	Registry registry;

	public static void main(String[] args) throws AlreadyBoundException {
		UserDao dao = new UserDaoImpl();
		try {
			dao.getAllUsers().forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		launch(args);
	}

	@Override
	public void init() throws Exception {

		super.init();
		RMIConnection.INSTANCE.init();

		//getting all offline users when server starts
		int allUsers = DaosFactory.INSTANCE.getStatisticsDao().getAllUsers();
		StatisticsData.INSTANCE.setOfflineUsers(allUsers);
		StatisticsData.INSTANCE.setAllUsers(allUsers);

		//getting all male/female users when server starts
		int maleUsers = DaosFactory.INSTANCE.getStatisticsDao().getMaleUsers();
		int femaleUsers =  DaosFactory.INSTANCE.getStatisticsDao().getFemaleUsers();

		StatisticsData.INSTANCE.setMaleUsers(maleUsers);
		StatisticsData.INSTANCE.setFemaleUsers(femaleUsers);

		//getting all countries/users when server starts
		StatisticsData.INSTANCE.setPieChartDataForCountry(DaosFactory.INSTANCE.getStatisticsDao().getAllCountries());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageCoordinator.initStage(primaryStage);
		stageCoordinator.switchToAnnouncementScene();
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		RMIConnection.INSTANCE.close();
		super.stop();
	}

}
