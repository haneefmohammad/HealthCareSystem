package com.healthcaresystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcaresystem.entity.Appointment;
import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.serviceimpl.AppointmentService;
import com.healthcaresystem.serviceimpl.DiagnosticCenterService;

import com.healthcaresystem.serviceimpl.TestService;
import com.healthcaresystem.serviceimpl.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	DiagnosticCenterService diagnosticCentreService;

	@Autowired
	AppointmentService appointmentService;

	@Autowired
	TestService testService;

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable int userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(userId));

	}

	@PostMapping("/addCenter")
	public ResponseEntity<String> addCenter(@RequestBody DiagnosticCenter diagnosticCenter) {
		String addedCenter = diagnosticCentreService.addDiagnosticCenter(diagnosticCenter);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedCenter);
	}

	@GetMapping("/allCenters")
	public ResponseEntity<List<DiagnosticCenter>> getAllCenters() {
		List<DiagnosticCenter> allCenters = diagnosticCentreService.getAllCenters();
		return ResponseEntity.ok(allCenters);
	}

	@DeleteMapping("/removeCenter/{centerId}")
	public ResponseEntity<String> removeCenter(@PathVariable("centerId") int centerId) {
		return ResponseEntity.status(HttpStatus.OK).body(diagnosticCentreService.removeCenter(centerId));
	}

	@PostMapping("/addTest/{centerId}")
	public ResponseEntity<String> addTest(@PathVariable int centerId, @RequestBody Tests test) {
		String addedTest = testService.addTestToDiagnosticCenter(centerId, test);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedTest);
	}

	@DeleteMapping("/{centerId}/removeTest/{testId}")
	public ResponseEntity<String> removeTest(@PathVariable int centerId, @PathVariable int testId) {
		return new ResponseEntity<String>(testService.removeTestFromDiagnosticCenter(centerId, testId), HttpStatus.OK);
	}

	@GetMapping("/pendingAppointments")
	public ResponseEntity<List<Appointment>> getPendingAppointment() {
		List<Appointment> pendingAppointments = appointmentService.getPendingAppointments();
		return ResponseEntity.ok(pendingAppointments);
	}

	@PostMapping("/approveAppointments/{appointmentId}/{centerId}")
	public ResponseEntity<String> approveAppointments(@PathVariable int appointmentId, @PathVariable int centerId) {
		appointmentService.approveAppointment(appointmentId, centerId);
		return ResponseEntity.ok("Appointment Approved");
	}

}
