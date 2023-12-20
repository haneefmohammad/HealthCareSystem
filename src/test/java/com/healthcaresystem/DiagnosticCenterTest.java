package com.healthcaresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.healthcaresystem.dto.TestCenterInfoDTO;
import com.healthcaresystem.entity.Appointment;
import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.exception.DiagnosticCenterNotFoundException;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;
import com.healthcaresystem.serviceimpl.DiagnosticCenterService;
import com.healthcaresystem.serviceimpl.TestService;

@SpringBootTest
public class DiagnosticCenterTest {

	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;

	@Mock
	private TestRepository testRepository;

	@InjectMocks
	private DiagnosticCenterService diagnosticCenterService;

	@InjectMocks
	private TestService testService;
	@Test
	void testAddDiagnosticCenter() {
		// Create default tests
		Tests bloodGroupTest = new Tests();
		bloodGroupTest.setTestId(1);
		bloodGroupTest.setTestName("Blood Group");

		Tests bloodSugarTest = new Tests();
		bloodSugarTest.setTestId(2);
		bloodSugarTest.setTestName("Blood Sugar");

		Tests bloodPressureTest = new Tests();
		bloodPressureTest.setTestId(3);
		bloodPressureTest.setTestName("Blood Pressure");

		List<Tests> defaultTests = Arrays.asList(bloodGroupTest, bloodSugarTest, bloodPressureTest);

		DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
		diagnosticCenter.setCenterId(1);
		diagnosticCenter.setCenterName("Haneef_Test_Center");

		// Mocking repository calls
		when(testRepository.findByTestNameIgnoreCase("Blood Group")).thenReturn(bloodGroupTest);
		when(testRepository.findByTestNameIgnoreCase("Blood Sugar")).thenReturn(bloodSugarTest);
		when(testRepository.findByTestNameIgnoreCase("Blood Pressure")).thenReturn(bloodPressureTest);

		when(diagnosticCenterRepository.save(any(DiagnosticCenter.class))).thenReturn(diagnosticCenter);

		// Call the service method
		String result = diagnosticCenterService.addDiagnosticCenter(diagnosticCenter);

		// Assertions
		assertEquals("Center added successfully", result);
		assertEquals(defaultTests, diagnosticCenter.getListOfTests());
	}

	@Test
	void testGetAllCenters() {
		// Create some dummy diagnostic centers
		DiagnosticCenter center1 = new DiagnosticCenter();
		center1.setCenterId(1);
		center1.setCenterName("Center 1");

		DiagnosticCenter center2 = new DiagnosticCenter();
		center2.setCenterId(2);
		center2.setCenterName("Center 2");

		List<DiagnosticCenter> centers = Arrays.asList(center1, center2);

		// Mocking repository call
		when(diagnosticCenterRepository.findAll()).thenReturn(centers);

		// Call the service method
		List<DiagnosticCenter> result = diagnosticCenterService.getAllCenters();

		// Assertions
		assertEquals(2, result.size());
		assertEquals(centers, result);
	}

	@Test
	void testRemoveCenter() {
		int centerId = 1;
		DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
		diagnosticCenter.setCenterId(centerId);
		diagnosticCenter.setCenterName("Test Center");

		// Mocking repository call
		when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(diagnosticCenter));
		when(diagnosticCenterRepository.save(any(DiagnosticCenter.class))).thenReturn(diagnosticCenter);

		// Call the service method
		String result = diagnosticCenterService.removeCenter(centerId);

		// Assertions
		assertEquals("center removed", result);
		verify(diagnosticCenterRepository, times(1)).delete(diagnosticCenter);
	}
	
	@Test
	public void testRemoveCenter_ValidCenterID() {
	    int centerId = 1;
	    DiagnosticCenter centerToRemove = new DiagnosticCenter(/* fill center details */);

	    when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(centerToRemove));

	    String result = diagnosticCenterService.removeCenter(centerId);

	    assertEquals("center removed", result);

	    verify(diagnosticCenterRepository, times(1)).findById(centerId);
	    verify(diagnosticCenterRepository, times(1)).delete(centerToRemove);
	    verifyNoMoreInteractions(diagnosticCenterRepository); // Ensure no other interactions with the repository
	}

	
	@Test
	void testAddDiagnosticCenterWithExistingTests() {
	    // Create default tests
	    Tests bloodGroupTest = new Tests();
	    bloodGroupTest.setTestId(1);
	    bloodGroupTest.setTestName("Blood Group");

	    Tests bloodSugarTest = new Tests();
	    bloodSugarTest.setTestId(2);
	    bloodSugarTest.setTestName("Blood Sugar");

	    Tests bloodPressureTest = new Tests();
	    bloodPressureTest.setTestId(3);
	    bloodPressureTest.setTestName("Blood Pressure");

	    // Mocking repository calls
	    when(testRepository.findByTestNameIgnoreCase("Blood Group")).thenReturn(bloodGroupTest);
	    when(testRepository.findByTestNameIgnoreCase("Blood Sugar")).thenReturn(bloodSugarTest);
	    when(testRepository.findByTestNameIgnoreCase("Blood Pressure")).thenReturn(bloodPressureTest);

	    DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
	    diagnosticCenter.setCenterId(1);
	    diagnosticCenter.setCenterName("Center_With_Existing_Tests");

	    // Call the service method
	    String result = diagnosticCenterService.addDiagnosticCenter(diagnosticCenter);

	    // Assertions
	    assertEquals("Center added successfully", result);
	    assertEquals(3, diagnosticCenter.getListOfTests().size()); // Ensure all existing tests are added
	}

	
	@Test
	void testRemoveNonExistentCenter() {
	    int centerId = 1; // Non-existent center ID

	    // Mocking repository call for a non-existent center
	    when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.empty());

	    // Call the service method and catch the exception
	    DiagnosticCenterNotFoundException exception = assertThrows(DiagnosticCenterNotFoundException.class,
	            () -> diagnosticCenterService.removeCenter(centerId));

	    // Assertions
	    assertEquals("Center not found with id", exception.getMessage()); // Ensure proper exception message
	    verify(diagnosticCenterRepository, never()).delete(any()); // Verify delete was never called
	}

	
	@Test
	void testRemoveCenterWithAppointments() {
	    int centerId = 1;
	    DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
	    diagnosticCenter.setCenterId(centerId);
	    diagnosticCenter.setCenterName("Center_With_Appointments");
	    // Simulate appointments by adding them to the center
	    List<Appointment> appointments = new ArrayList<>();
	    // Populate appointments list...
	    diagnosticCenter.setAppointmentList(appointments);

	    // Mocking repository calls
	    when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(diagnosticCenter));
	    when(diagnosticCenterRepository.save(any(DiagnosticCenter.class))).thenReturn(diagnosticCenter);

	    // Call the service method
	    String result = diagnosticCenterService.removeCenter(centerId);

	    // Assertions
	    assertEquals("center removed", result);
	    assertEquals(0, diagnosticCenter.getAppointmentList().size()); // Ensure appointments are removed
	    verify(diagnosticCenterRepository, times(1)).delete(diagnosticCenter); // Verify delete was called
	}
	
	@Test
	void testRemoveTestWhenCenterIsRemoved() {
	    // Mock center and test data
	    int centerId = 1;
	    int testIdToRemove = 2; // Assuming a specific test ID to remove

	    DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
	    diagnosticCenter.setCenterId(centerId);
	    diagnosticCenter.setCenterName("Test_Center_With_Tests");

	    Tests test1 = new Tests();
	    test1.setTestId(1);
	    test1.setTestName("Test1");

	    Tests testToRemove = new Tests();
	    testToRemove.setTestId(testIdToRemove);
	    testToRemove.setTestName("TestToRemove");

	    Tests test3 = new Tests();
	    test3.setTestId(3);
	    test3.setTestName("Test3");

	    List<Tests> testsList = Arrays.asList(test1, testToRemove, test3);
	    diagnosticCenter.setListOfTests(testsList);

	    // Mock repository behavior
	    when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(diagnosticCenter));
	    when(testRepository.findById(testIdToRemove)).thenReturn(Optional.of(testToRemove));
	    when(diagnosticCenterRepository.save(any(DiagnosticCenter.class))).thenAnswer(invocation -> invocation.getArgument(0));

	    // Call the service method to remove the center
	    diagnosticCenterService.removeCenter(centerId);

	    // Check if the removed center doesn't have any tests
	    assertEquals(0, diagnosticCenter.getListOfTests().size()); // Ensure all tests are removed when the center is removed
	}
	 
	@Test
    public void testGetTestCenterInfo() {
        // Mocking diagnostic center data
        DiagnosticCenter diagnosticCenter1 = new DiagnosticCenter();
        diagnosticCenter1.setCenterId(1);
        diagnosticCenter1.setCenterName("Center 1");
        diagnosticCenter1.setCenterAddress("Address 1");
        
        Tests test1 = new Tests();
        test1.setTestId(101);
        test1.setTestName("Test A");

        // Associate the test with the diagnostic center
        diagnosticCenter1.getListOfTests().add(test1);

        // Add more diagnostic centers and tests if needed

        // Mock behavior of diagnosticCenterRepository.findAll()
        when(diagnosticCenterRepository.findAll()).thenReturn(List.of(diagnosticCenter1 /*, add more diagnostic centers if needed */));

        // Call the method to fetch test center info
        List<TestCenterInfoDTO> testCenterInfoList = diagnosticCenterService.getTestCenterInfo();

        // Assertions
        assertNotNull(testCenterInfoList);
        assertEquals(1, testCenterInfoList.size());

        TestCenterInfoDTO testCenterInfoDTO = testCenterInfoList.get(0);
        assertEquals(1, testCenterInfoDTO.getCenterId());
        assertEquals("Center 1", testCenterInfoDTO.getCenterName());
        assertEquals("Address 1", testCenterInfoDTO.getCenterAddress());
        assertEquals(101, testCenterInfoDTO.getTestId());
        assertEquals("Test A", testCenterInfoDTO.getTestName());

       
    }

}



