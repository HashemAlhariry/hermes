package gov.iti.jets.server;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import common.business.services.Login;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.daos.impl.UserDaoImpl;
import gov.iti.jets.server.presentation.gui.util.StageCoordinator;
import gov.iti.jets.server.presentation.network.RMIConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private StageCoordinator stageCoordinator = StageCoordinator.INSTANCE;
	Registry registry;
	Login login;

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
