package com.healthcaresystem.exception;



public class UserAlreadyHaveAppointmentException extends RuntimeException{
	public UserAlreadyHaveAppointmentException(String message)
	{
		super(message);
	}

}
