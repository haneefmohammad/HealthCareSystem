package com.healthcaresystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcaresystem.dto.MakeAppointmentDTO;
import com.healthcaresystem.dto.TestCenterInfoDTO;
import com.healthcaresystem.dto.UserDTO;

import com.healthcaresystem.entity.User;
import com.healthcaresystem.serviceimpl.AppointmentService;
import com.healthcaresystem.serviceimpl.DiagnosticCenterService;
import com.healthcaresystem.serviceimpl.UserService;

@RestController
@RequestMapping("/users")
public class CustomerController {
	@Autowired
	private UserService userService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private DiagnosticCenterService diagnosticCenterService;
	

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
	
	@GetMapping("/login/viewCenterTest")
	public ResponseEntity<List<TestCenterInfoDTO>> viewCenterTests()
	{
		List<TestCenterInfoDTO> viewAllDetails = diagnosticCenterService.getTestCenterInfo();
		return ResponseEntity.ok(viewAllDetails);
	}

}