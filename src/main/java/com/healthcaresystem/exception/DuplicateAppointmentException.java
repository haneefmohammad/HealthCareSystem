package com.healthcaresystem.exception;

public class DuplicateAppointmentException extends RuntimeException{
	
	public DuplicateAppointmentException(String message)
	{
		super(message);
	} 
}
