package com.ferbo.facturama.tools;

public class FacturamaInternalException extends Exception{
	
	private static final long serialVersionUID = 3908683453869909723L;

	public FacturamaInternalException() {
		super();
	}
	
	public FacturamaInternalException(String message) {
		super(message);
	}
	
	public FacturamaInternalException(Throwable cause) {
		super(cause);
	}
	
	public FacturamaInternalException(String message, Throwable cause) {
		super(message, cause);
	}

}