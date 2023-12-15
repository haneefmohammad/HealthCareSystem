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
import com.healthcaresystem.entity.Test;
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
		try {
			String message = userService.registerNewUser(user);
			user.getUserName();
			user.getUserPassword();
			user.getUserEmail();
			user.getAge();
			user.getGender();
			user.getPhoneNumber();

			return ResponseEntity.status(HttpStatus.CREATED).body(message);
		} catch (InvalidPasswordException | InvalidPhoneNumberException | InvalidUserNameException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (UserException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
		}

	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
		String userEmail = credentials.get("email");
		String userPassword = credentials.get("password");

		System.out.println(userEmail + " " + userPassword);

		try {
			String loginStatus = userService.loginUser(userEmail, userPassword);

			if (loginStatus.equals("Login Successful")) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginStatus);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginStatus);
			}
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		} catch (InvalidLoginException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

		}
	}

	@PutMapping("/updateDetails/{userId}")
	public ResponseEntity<User> updateDetails(@PathVariable int userId, @RequestBody User updateUser) {
		User updateUserDetails = userService.updatedUserDetails(userId, updateUser);
		return ResponseEntity.ok(updateUserDetails);
	}

	@PostMapping("/login/makeAppointment")
	public ResponseEntity<String> makeAppointment(@RequestBody MakeAppointmentDTO makeAppointmentDTO) {

		System.out.println(makeAppointmentDTO);
		
		int userId = makeAppointmentDTO.getUserId();
		int centerId = makeAppointmentDTO.getCenterId();
		List<Test> testIds = makeAppointmentDTO.getTestIds();
		LocalDateTime appointmentDate = makeAppointmentDTO.getDateTime();
		System.out.println(makeAppointmentDTO);
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Id not found"));
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepository.findByCenterId(centerId)
				.orElseThrow(() -> new DiagnosticCenterNotFoundException("Center id not found"));

		System.out.println(makeAppointmentDTO.getDateTime());
		appointmentService.makeAppointment(user, diagnosticCenter, testIds, appointmentDate);
		return ResponseEntity.ok("Appointment Added Successfully");

	}

}