package com.healthcaresystem.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.entity.User;
import com.healthcaresystem.dto.AppointmentDetailsDTO;
import com.healthcaresystem.dto.MakeAppointmentDTO;
import com.healthcaresystem.entity.Appointment;
import com.healthcaresystem.exception.AppointmentNotFoundException;
import com.healthcaresystem.exception.DiagnosticCenterNotFoundException;
import com.healthcaresystem.exception.DuplicateAppointmentException;
import com.healthcaresystem.exception.InvalidAppointmentDateException;
import com.healthcaresystem.exception.InvalidCenterIdException;
import com.healthcaresystem.exception.TestNotFoundException;
import com.healthcaresystem.exception.UserAlreadyHaveAppointmentException;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.repository.AppointmentRepository;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.UserRepository;

@Service
public class AppointmentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;


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
		
		if(existingUser.get().getAppointment() != null) {
			throw new UserAlreadyHaveAppointmentException("User already haven an appointment");
		}
		
		List<Tests> availableTests = diagnosticCenter.getListOfTests();
			
		if (availableTests.stream().filter(a -> a.getTestId() == testId).count() == 0L)
			throw new TestNotFoundException("No test found");

		LocalDateTime currentDateTime = LocalDateTime.now();

		if (appointmentDate == null || appointmentDate.isBefore(currentDateTime)) {
			throw new InvalidAppointmentDateException("invalid appointment date");
		}
		
		
		

		Tests test1 = availableTests.stream().filter(a -> a.getTestId() == testId).findFirst().get();
		if (appointmentRepository.existsByUserAndTestAndDiagnosticCenterAndDateTime(
                existingUser.get(), test1, diagnosticCenter, makeAppointmentDTO.getDateTime()) ){
            throw new DuplicateAppointmentException("Duplicate appointment found for user, test, center, and date.");
        }
//		
//		
		
		
		Appointment appointment = new Appointment();
		appointment.setUser(existingUser.get());
		appointment.setDiagnosticCenter(existsCenter.get());
		appointment.setTest(test1);
		appointment.setDateTime(appointmentDate);
		appointment.setApproved(false);

		

		
		
		existingUser.get().setAppointment(appointment);
		existingUser.get().setDiagnosticCenter(diagnosticCenter);
		userRepository.save(existingUser.get());
	}

	public List<AppointmentDetailsDTO> getPendingAppointments() {
		List<Appointment> appointments = appointmentRepository.findAll();
		return appointments.stream().filter(a -> !a.getApproved()).map(this::mapTOAppointmentDetails).collect(Collectors.toList());
		
	}
	private AppointmentDetailsDTO mapTOAppointmentDetails(Appointment appointment)
	{
		AppointmentDetailsDTO detailsDTO = new AppointmentDetailsDTO();
		detailsDTO.setAppointmentId(appointment.getAppointmentId());
		detailsDTO.setCenterId(appointment.getDiagnosticCenter().getCenterId());
		detailsDTO.setCenterName(appointment.getDiagnosticCenter().getCenterName());
		detailsDTO.setTestId(appointment.getTest().getTestId());
		detailsDTO.setTestName(appointment.getTest().getTestName());
		detailsDTO.setUserID(appointment.getUser().getUserId());
		detailsDTO.setApproved(appointment.getApproved());
		
		return detailsDTO;
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
