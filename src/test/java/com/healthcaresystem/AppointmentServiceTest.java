package com.healthcaresystem;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.healthcaresystem.dto.MakeAppointmentDTO;
import com.healthcaresystem.entity.Appointment;
import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.entity.User;
import com.healthcaresystem.exception.AppointmentNotFoundException;
import com.healthcaresystem.exception.DiagnosticCenterNotFoundException;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.repository.AppointmentRepository;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;
import com.healthcaresystem.repository.UserRepository;
import com.healthcaresystem.serviceimpl.AppointmentService;

@SpringBootTest
public class AppointmentServiceTest {

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
        // Mocking necessary data
        int userId = 1;
        int centerId = 1;
        int testId = 1;
        LocalDateTime appointmentDate = LocalDateTime.now();

        MakeAppointmentDTO makeAppointmentDTO = new MakeAppointmentDTO();
        makeAppointmentDTO.setUserId(userId);
        makeAppointmentDTO.setCenterId(centerId);
        makeAppointmentDTO.setTestId(testId);
        makeAppointmentDTO.setDateTime(appointmentDate);

        User user = new User(); // Populate user object
        DiagnosticCenter diagnosticCenter = new DiagnosticCenter(); // Populate diagnosticCenter object
        Tests test = new Tests(); // Populate test object

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(diagnosticCenter));
        when(testRepository.findById(testId)).thenReturn(Optional.of(test));

        // Test appointment creation
        appointmentService.makeAppointment(makeAppointmentDTO);
        verify(userRepository, times(1)).findById(userId);
        verify(diagnosticCenterRepository, times(1)).findById(centerId);
        verify(testRepository, times(1)).findById(testId);
        // Add assertions for expected behavior based on the above input
    }

    @Test
     void testMakeAppointment_InvalidUserID() {
    	 int userId = 2;
         int centerId = 1;
         int testId = 1;
         LocalDateTime appointmentDate = LocalDateTime.now();

         MakeAppointmentDTO makeAppointmentDTO = new MakeAppointmentDTO();
         makeAppointmentDTO.setUserId(userId);
         makeAppointmentDTO.setCenterId(centerId);
         makeAppointmentDTO.setTestId(testId);
         makeAppointmentDTO.setDateTime(appointmentDate);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> appointmentService.makeAppointment(makeAppointmentDTO));
    }

    @Test
     void testMakeAppointment_InvalidCenterID() {
    	 int userId = 1;
         int centerId = 2;
         int testId = 1;
         LocalDateTime appointmentDate = LocalDateTime.now();

         MakeAppointmentDTO makeAppointmentDTO = new MakeAppointmentDTO();
         makeAppointmentDTO.setUserId(userId);
         makeAppointmentDTO.setCenterId(centerId);
         makeAppointmentDTO.setTestId(testId);
         makeAppointmentDTO.setDateTime(appointmentDate);
         
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
        when(diagnosticCenterRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(DiagnosticCenterNotFoundException.class, () -> appointmentService.makeAppointment(makeAppointmentDTO));
    }
    @Test
     void testGetPendingAppointments() {
        // Mocking appointments data
        List<Appointment> appointments = new ArrayList<>(); // Populate appointments list
        when(appointmentRepository.findAll()).thenReturn(appointments);

        // Test the method and verify the expected outcome
        List<Appointment> pendingAppointments = appointmentService.getPendingAppointments();

        // Add assertions to verify pending appointments 
        assertFalse(pendingAppointments.isEmpty()); // Check if the list is not empty

        for (Appointment appointment : pendingAppointments) {
            assertFalse(appointment.getApproved());
    }

    }
    @Test
    void testApproveAppointment_AppointmentNotFound() {
        int appointmentId = 999; // Set an ID that doesn't exist

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.approveAppointment(appointmentId, anyInt()));
    }
  
//	@InjectMocks
//    private AppointmentService appointmentService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private DiagnosticCenterRepository diagnosticCenterRepository;
//
//    @Mock
//    private TestRepository testRepository;
//
//    @Mock
//    private AppointmentRepository appointmentRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//    
//    @Test
//    void testMakeAppointment() {
//        // Arrange
//        User user = new User();
//        user.setUserId(1); // Adjust the user ID as needed
//
//        DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
//        diagnosticCenter.setCenterId(1); // Adjust the center ID as needed
//
//        List<Tests> testList = new ArrayList<>(); // Add some test objects to the list
//
//        LocalDateTime appointmentDate = LocalDateTime.now().plusDays(1); // Adjust the date as needed
//
//        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
//        when(diagnosticCenterRepository.findById(anyInt())).thenReturn(Optional.of(diagnosticCenter));
//        when(testRepository.findByDiagnosticCenter(any(DiagnosticCenter.class))).thenReturn(testList);
//
//        // Act
//       // assertDoesNotThrow(() -> appointmentService.makeAppointment(MakeAppointmentDTO));
//
//        // Assert
//        verify(appointmentRepository, times(1)).save(any(Appointment.class));
//    }
//
//    @Test
//    void testGetPendingAppointments() {
//        // Arrange
//        List<Appointment> appointments = new ArrayList<>(); // Add some appointments to the list
//        when(appointmentRepository.findAll()).thenReturn(appointments);
//
//        // Act
//        List<Appointment> pendingAppointments = appointmentService.getPendingAppointments();
//
//        // Assert
//        assertNotNull(pendingAppointments);
//        // Add assertions based on your expectations
//    }
//
//    
//    @Test
//    void testApproveAppointment() {
//        // Arrange
//        int appointmentId = 1; // Adjust the appointment ID as needed
//        int centerId = 1; // Adjust the center ID as needed
//
//        Appointment appointment = new Appointment();
//        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        AppointmentNotFoundException exception = assertThrows(AppointmentNotFoundException.class, () -> {
//            appointmentService.approveAppointment(appointmentId, centerId);
//        });
//
//        assertEquals("appointment not found", exception.getMessage());
//        verify(appointmentRepository, times(1)).findById(appointmentId);
//        verify(appointmentRepository, never()).save(any(Appointment.class));
//        verify(userRepository, never()).save(any(User.class));
//    
//}

}
