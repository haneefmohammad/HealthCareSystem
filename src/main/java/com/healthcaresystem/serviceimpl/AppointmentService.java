package com.healthcaresystem.serviceimpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.entity.User;
import com.healthcaresystem.dto.MakeAppointmentDTO;
import com.healthcaresystem.entity.Appointment;
import com.healthcaresystem.exception.AppointmentNotFoundException;
import com.healthcaresystem.exception.DiagnosticCenterNotFoundException;
import com.healthcaresystem.exception.InvalidAppointmentDateException;
import com.healthcaresystem.exception.InvalidCenterIdException;
import com.healthcaresystem.exception.TestNotFoundException;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.repository.AppointmentRepository;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;
import com.healthcaresystem.repository.UserRepository;

@Service
public class AppointmentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	public void makeAppointment(MakeAppointmentDTO makeAppointmentDTO) {


		int userId = makeAppointmentDTO.getUserId();
		int centerId = makeAppointmentDTO.getCenterId();
		int testId = makeAppointmentDTO.getTestId();
		LocalDateTime appointmentDate = makeAppointmentDTO.getDateTime();
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Id not found"));
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepository.findById(centerId)
				.orElseThrow(() -> new DiagnosticCenterNotFoundException("Center id not found"));

		Optional<User> existingUser = userRepository.findById(user.getUserId());
		if (existingUser.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		Optional<DiagnosticCenter> existsCenter = diagnosticCenterRepository.findById(diagnosticCenter.getCenterId());
		if (existsCenter.isEmpty()) {
			throw new DiagnosticCenterNotFoundException("No diagnostic Center");
		}
		
		
		List<Tests> availableTests = diagnosticCenter.getListOfTests();
		
				
		if (availableTests.stream().filter(a -> a.getTestId() == testId).count() == 0L)
			throw new TestNotFoundException("No test found");
		//if(availableTests.isEmpty() || !availableTests.stream().anyMatch(a -> a.getTestId() == test))
//		{
//			throw new TestNotFoundException("No test found");
//		}
		LocalDateTime currentDateTime = LocalDateTime.now();

		if (appointmentDate == null) {
			throw new InvalidAppointmentDateException("invalid appointment date");
		}
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//		String formattedDateTime = appointmentDate.format(formatter);
		Tests test1 = availableTests.stream().filter(a -> a.getTestId() == testId).findFirst().get();
		Appointment appointment = new Appointment();
		appointment.setUser(existingUser.get());
		appointment.setDiagnosticCenter(existsCenter.get());
		appointment.setTest(test1);
		appointment.setDateTime(appointmentDate);
		appointment.setApproved(false);

		

		//appointmentRepository.save(appointment);
		
		existingUser.get().setAppointment(appointment);
		existingUser.get().setDiagnosticCenter(diagnosticCenter);
		userRepository.save(existingUser.get());
	}

	public List<Appointment> getPendingAppointments() {
		List<Appointment> appointments = appointmentRepository.findAll();
		return appointments.stream().filter(a -> !a.getApproved()).toList();
	}

	public void approveAppointment(int appointmentId, int centerID) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new AppointmentNotFoundException("appointment not found"));

		if (appointment.getDiagnosticCenter().getCenterId() != centerID) {
			throw new InvalidCenterIdException("Invalid Center Id found");
		}

		appointment.setApproved(true);
		appointmentRepository.save(appointment);

		User user = appointment.getUser();
		userRepository.save(user);

	}

}
