package exception;

public class LimitLoginException extends Exception {

	private static String message = "Maximum number of login attempts reached. The application will close";
	private static final long serialVersionUID = 1L;

	// Call the constructor
	public LimitLoginException() {
		super(message);
	}

}
