package com.ferbo.facturama.tools;

public class FacturamaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FacturamaException() {
		super();
	}
	
	public FacturamaException(String message) {
		super(message);
	}
	
	public FacturamaException(Throwable cause) {
		super(cause);
	}
	
	public FacturamaException(String message, Throwable cause) {
		super(message, cause);
	}

}
