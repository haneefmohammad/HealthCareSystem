package com.healthcaresystem.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcaresystem.dto.MakeAppointmentDTO;
import com.healthcaresystem.dto.UserDTO;
import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.entity.User;
import com.healthcaresystem.exception.DiagnosticCenterNotFoundException;
import com.healthcaresystem.exception.InvalidLoginException;
import com.healthcaresystem.exception.InvalidPasswordException;
import com.healthcaresystem.exception.InvalidPhoneNumberException;
import com.healthcaresystem.exception.InvalidUserNameException;
import com.healthcaresystem.exception.UserException;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.UserRepository;
import com.healthcaresystem.serviceimpl.AppointmentService;
import com.healthcaresystem.serviceimpl.UserService;

@RestController
@RequestMapping("/users")
public class CustomerController {
	@Autowired
	private UserService userService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerNewUser(user));
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(credentials));

	}

	@PutMapping("/updateDetails/{userId}")
	public ResponseEntity<User> updateDetails(@PathVariable int userId, @RequestBody User updateUser) {
		User updateUserDetails = userService.updatedUserDetails(userId, updateUser);
		return ResponseEntity.ok(updateUserDetails);
	}

	@PostMapping("/login/makeAppointment")
	public ResponseEntity<String> makeAppointment(@RequestBody MakeAppointmentDTO makeAppointmentDTO) {
	appointmentService.makeAppointment(makeAppointmentDTO);
		return ResponseEntity.ok("Appointment  Added Successfully");

	}

}