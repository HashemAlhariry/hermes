package gov.iti.jets.server.persistance;

import java.sql.SQLException;

public class PersistenceException extends SQLException {

	public PersistenceException(String message) {
		super(message);
	}

}
