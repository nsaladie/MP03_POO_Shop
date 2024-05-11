package exception;

public class LimitLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Call the constructor
	public LimitLoginException(String message) {
		super(message);
	}

}
