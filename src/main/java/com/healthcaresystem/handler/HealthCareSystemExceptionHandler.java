package com.healthcaresystem.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.healthcaresystem.exception.AppointmentNotFoundException;
import com.healthcaresystem.exception.DiagnosticCenterException;
import com.healthcaresystem.exception.DiagnosticCenterNotFoundException;
import com.healthcaresystem.exception.DuplicateAppointmentException;
import com.healthcaresystem.exception.InvalidAppointmentDateException;
import com.healthcaresystem.exception.InvalidCenterIdException;
import com.healthcaresystem.exception.InvalidLoginException;
import com.healthcaresystem.exception.InvalidPasswordException;
import com.healthcaresystem.exception.InvalidPhoneNumberException;
import com.healthcaresystem.exception.InvalidUserNameException;
import com.healthcaresystem.exception.TestException;
import com.healthcaresystem.exception.TestNotFoundException;
import com.healthcaresystem.exception.UserAlreadyHaveAppointmentException;
import com.healthcaresystem.exception.UserException;
import com.healthcaresystem.exception.UserNotFoundException;

@ControllerAdvice
public class HealthCareSystemExceptionHandler {

	private ApiError error = new ApiError();
	@ExceptionHandler(AppointmentNotFoundException.class)
	public ResponseEntity<ApiError> appointmentNotFoundException( AppointmentNotFoundException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(DiagnosticCenterException.class)
	public ResponseEntity<ApiError> diagnosticCenterException( DiagnosticCenterException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(DiagnosticCenterNotFoundException.class)
	public ResponseEntity<ApiError> diagnosticCenterNotFoundException( DiagnosticCenterNotFoundException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidAppointmentDateException.class)
	public ResponseEntity<ApiError> invalidAppointmentDateException( InvalidAppointmentDateException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidCenterIdException.class)
	public ResponseEntity<ApiError> invalidCenterIdException( InvalidCenterIdException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	

	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<ApiError> invalidLoginException( InvalidLoginException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ApiError> invalidPasswordException( InvalidPasswordException ex)
	{
		error.setStatus(HttpStatus.NOT_ACCEPTABLE);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidPhoneNumberException.class)
	public ResponseEntity<ApiError> invalidPhoneNumberException( InvalidPhoneNumberException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidUserNameException.class)
	public ResponseEntity<ApiError> invalidUserNameException( InvalidUserNameException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(TestException.class)
	public ResponseEntity<ApiError> testException( TestException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(TestNotFoundException.class)
	public ResponseEntity<ApiError> testNotFoundException( TestNotFoundException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ApiError> userException( UserException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> userNotFoundException( UserNotFoundException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(DuplicateAppointmentException.class)
	public ResponseEntity<ApiError> duplicateAppointmentException( DuplicateAppointmentException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(UserAlreadyHaveAppointmentException.class)
	public ResponseEntity<ApiError> userAlreadyHaveAppointmentException( UserAlreadyHaveAppointmentException ex)
	{
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

}
