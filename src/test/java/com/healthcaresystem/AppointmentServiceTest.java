package com.healthcaresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.healthcaresystem.dto.AppointmentDetailsDTO;
import com.healthcaresystem.dto.MakeAppointmentDTO;
import com.healthcaresystem.entity.Appointment;
import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.entity.User;
import com.healthcaresystem.exception.AppointmentNotFoundException;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.repository.AppointmentRepository;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;
import com.healthcaresystem.repository.UserRepository;
import com.healthcaresystem.serviceimpl.AppointmentService;

@SpringBootTest
 class AppointmentServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;

	@Mock
	private TestRepository testRepository;

	@Mock
	private AppointmentRepository appointmentRepository;

	@InjectMocks
	private AppointmentService appointmentService;
	@Test
    void testMakeAppointment_ValidAppointment() {
        MockitoAnnotations.openMocks(this);

        // Create a sample MakeAppointmentDTO
        MakeAppointmentDTO appointmentDTO = new MakeAppointmentDTO();
        appointmentDTO.setUserId(1);
        appointmentDTO.setCenterId(1);
        appointmentDTO.setTestId(1);
        appointmentDTO.setDateTime(LocalDateTime.now().plusDays(1)); // Set an appointment for tomorrow

        // Create a mock user
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setUserName("John Doe");
        // ... Set other relevant user details

        // Create a mock diagnostic center
        DiagnosticCenter mockCenter = new DiagnosticCenter();
        mockCenter.setCenterId(1);
        mockCenter.setCenterName("Example Center");
        // ... Set other relevant center details

        // Create a mock test
        Tests test = new Tests();
        test.setTestId(1);
        test.setTestName("Example Test");
        // ... Set other relevant test details

        // Create a list of available tests in the mock diagnostic center
        List<Tests> availableTests = new ArrayList<>();
        availableTests.add(test);
        mockCenter.setListOfTests(availableTests);

        // Mock repository behaviors
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenAnswer(invocation -> {
            int userId = invocation.getArgument(0);
            if (userId == 1) {
                return Optional.of(mockUser);
            } else {
                return Optional.empty();
            }
        });

        Mockito.when(diagnosticCenterRepository.findById(Mockito.anyInt())).thenAnswer(invocation -> {
            int centerId = invocation.getArgument(0);
            if (centerId == 1) {
                return Optional.of(mockCenter);
            } else {
                return Optional.empty();
            }
        });

        // Call the method under test
        appointmentService.makeAppointment(appointmentDTO);

        // Additional assertions based on the expected behavior after making an appointment
        Assertions.assertTrue(mockUser.getAppointment() != null); // Check if the user has an appointment
        Assertions.assertEquals(mockCenter, mockUser.getDiagnosticCenter()); // Check if user's center is set correctly
        Assertions.assertEquals(test, mockUser.getAppointment().getTest()); // Check if the test in the appointment is correct
        Assertions.assertEquals(appointmentDTO.getDateTime(), mockUser.getAppointment().getDateTime()); // Check appointment date
        Assertions.assertFalse(mockUser.getAppointment().getApproved()); // Check if appointment is not approved initially
    }



	
	

	@Test
	 void testUserNotFound() {
	    int nonExistingUserId = 999; // Non-existing user ID

	    // Mock MakeAppointmentDTO with a non-existing user ID
	    MakeAppointmentDTO makeAppointmentDTO = new MakeAppointmentDTO();
	    makeAppointmentDTO.setUserId(nonExistingUserId);

	    // Mock userRepository behavior
	    when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

	    // Call method and assert for UserNotFoundException
	    assertThrows(UserNotFoundException.class, () -> appointmentService.makeAppointment(makeAppointmentDTO));
	}

	@Test
	 void testDiagnosticCenterNotFound() {
	    int nonExistingCenterId = 999; // Non-existing center ID

	    // Mock MakeAppointmentDTO with a non-existing center ID
	    MakeAppointmentDTO makeAppointmentDTO = new MakeAppointmentDTO();
	    makeAppointmentDTO.setCenterId(nonExistingCenterId);

	    // Mock diagnosticCenterRepository behavior
	    when(diagnosticCenterRepository.findById(nonExistingCenterId)).thenReturn(Optional.empty());

	    // Call method and assert for DiagnosticCenterNotFoundException
	}



	@Test
    public void testGetPendingAppointments() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Mocking appointment data
		
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1); 
      
        DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
        diagnosticCenter.setCenterId(123); // Set appropriate ID
        diagnosticCenter.setCenterName("Example Center");
        
        Tests test = new Tests();
        test.setTestId(456); // Set appropriate ID
        test.setTestName("Example Test"); // Set appropriate name

        // Associate the appointment with the test
        appointment1.setDiagnosticCenter(diagnosticCenter);
        appointment1.setTest(test);

        // Creating and associating a User
        User user = new User();
        user.setUserId(789);

        appointment1.setUser(user);
        appointment1.setApproved(false); 
        
        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentId(2);
        DiagnosticCenter diagnosticCenter1 = new DiagnosticCenter();
        diagnosticCenter1.setCenterId(456); // Set appropriate ID
        diagnosticCenter1.setCenterName("Example2 Center"); // Set appropriate name

        // Associate the appointment with the diagnostic center
        appointment2.setDiagnosticCenter(diagnosticCenter1);

        // Creating and associating a Test
        Tests test1 = new Tests();
        test1.setTestId(789); // Set appropriate ID
        test1.setTestName("Example2 Test"); // Set appropriate name

        // Associate the appointment with the test
        appointment2.setTest(test);

        // Creating and associating a User
        User user1 = new User();
        user1.setUserId(123); // Set appropriate ID

        // Associate the appointment with the user
        appointment2.setUser(user);

        // Set the approval status or other properties if needed
        appointment2.setApproved(false);

        // Mock behavior of appointmentRepository.findAll()
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment1, appointment2));

        // Call the method to fetch pending appointments
        List<AppointmentDetailsDTO> pendingAppointments = appointmentService.getPendingAppointments();

        // Assertions
        assertNotNull(pendingAppointments);
        assertEquals(2, pendingAppointments.size()); // Assuming all appointments are approved, hence expecting an empty list

        // Testing private method mapTOAppointmentDetails using reflection
        Method mapToAppointmentDetailsMethod = AppointmentService.class.getDeclaredMethod("mapTOAppointmentDetails", Appointment.class);
        mapToAppointmentDetailsMethod.setAccessible(true);

        AppointmentDetailsDTO detailsDTO = (AppointmentDetailsDTO) mapToAppointmentDetailsMethod.invoke(appointmentService, appointment1);

        assertEquals(appointment1.getAppointmentId(), detailsDTO.getAppointmentId());
        assertEquals(appointment1.getDiagnosticCenter().getCenterId(), detailsDTO.getCenterId());
        assertEquals(appointment1.getDiagnosticCenter().getCenterName(), detailsDTO.getCenterName());
        // Add more assertions for other properties mapped in the AppointmentDetailsDTO
    }



	@Test
	void testApproveAppointment_AppointmentNotFound() {
		int appointmentId = 999; // Set an ID that doesn't exist

		when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

		assertThrows(AppointmentNotFoundException.class,
				() -> appointmentService.approveAppointment(appointmentId, anyInt()));
	}



}
