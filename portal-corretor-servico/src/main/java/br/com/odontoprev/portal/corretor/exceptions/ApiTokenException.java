package br.com.odontoprev.portal.corretor.exceptions;

public class ApiTokenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ApiTokenException(String message, Exception e) {
		super(message,e);
	}
}
