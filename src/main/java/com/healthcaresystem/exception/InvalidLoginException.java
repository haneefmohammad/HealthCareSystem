package com.healthcaresystem.exception;

public class InvalidLoginException extends RuntimeException {
	public InvalidLoginException(String message)
	{
		super(message);
	}

}
