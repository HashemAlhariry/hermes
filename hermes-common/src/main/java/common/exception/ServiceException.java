package common.exception;

/**
 *
 * @author mina
 */
public class ServiceException extends RuntimeException {

	// message is what's going to be shown to the user
	public ServiceException(String message) {
		super(message);
	}

}
