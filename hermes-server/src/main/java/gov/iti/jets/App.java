package gov.iti.jets;

import java.sql.SQLException;

import gov.iti.jets.business.daos.UserDao;
import gov.iti.jets.persistance.daos.impl.UserDaoImpl;
import gov.iti.jets.presentation.util.StageCoordinator;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	
	private StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;

	public static void main(String[] args) {
		UserDao dao = new UserDaoImpl();
		try {
			dao.getAllUsers().forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		stageCoordinator.initStage(primaryStage);
		stageCoordinator.switchToAnnouncementScene();
		primaryStage.show();
	}
}
