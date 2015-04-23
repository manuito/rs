package com.elecomte.rs.ble;

public class BLEStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4225008915504749895L;

	public BLEStackException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BLEStackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BLEStackException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BLEStackException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BLEStackException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Emmanuel Lecomte (elecomte)
	 *
	 */
	static class BLEStackExceptionHolder {
		private BLEStackException exc;

		/**
		 * @return the exc
		 */
		public BLEStackException getExc() {
			return this.exc;
		}

		/**
		 * @param exc
		 *            the exc to set
		 */
		public void setExc(BLEStackException exc) {
			this.exc = exc;
		}

	}

}
