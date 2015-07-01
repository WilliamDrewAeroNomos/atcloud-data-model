/**
 * 
 */
package com.atcloud.model;

/**
 * 
 * @author <a href=mailto:support@atcloud.com>support</a>
 * @version $Revision: $
 */

public class ATCloudDataModelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1482945861458481915L;

	/**
	 * 
	 */
	public ATCloudDataModelException() {

	}

	/**
	 * @param message
	 */
	public ATCloudDataModelException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ATCloudDataModelException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ATCloudDataModelException(String message, Throwable cause) {
		super(message, cause);
	}

}
