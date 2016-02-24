/**
 * 
 */
package bugtrap03.permission;

/**
 * @author Ben Goethuys & Vincent Derkinderen
 *
 */
public class PermissionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7763416390411941211L;

	/**
	 * 
	 */
	public PermissionException() {
	}

	/**
	 * @param message
	 */
	public PermissionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PermissionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
