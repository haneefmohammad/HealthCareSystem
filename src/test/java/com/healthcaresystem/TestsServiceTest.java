package com.healthcaresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.exception.DiagnosticCenterException;
import com.healthcaresystem.exception.TestException;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;
import com.healthcaresystem.serviceimpl.TestService;

@SpringBootTest
public class TestsServiceTest {
	
	 @Mock
	    private TestRepository testRepository;

	    @Mock
	    private DiagnosticCenterRepository diagnosticCenterRepository;

	    @InjectMocks
	    private TestService testService;

	    @Test
	    void testAddTestToDiagnosticCenter_Success() throws DiagnosticCenterException {
	        // Mocking data
	        int centerId = 1;
	        Tests test = new Tests();
	        test.setTestId(1);
	        test.setTestName("New Test");

	        DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
	        diagnosticCenter.setCenterId(centerId);
	        diagnosticCenter.setListOfTests(new ArrayList<>());

	        when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(diagnosticCenter));
	        when(testRepository.save(test)).thenReturn(test);
	        when(diagnosticCenterRepository.save(diagnosticCenter)).thenReturn(diagnosticCenter);

	        // Call the service method
	        String result = testService.addTestToDiagnosticCenter(centerId, test);

	        
	        assertEquals("Test added to Diagnostic Center successfully.", result);
	        assertTrue(test.getDiagnosticCenter().contains(diagnosticCenter)); 


	        verify(diagnosticCenterRepository, times(1)).save(diagnosticCenter);
	    }




	    @Test
	    void testAddTestToDiagnosticCenter_TestAlreadyExists() throws DiagnosticCenterException {
	        // Mocking data
	        int centerId = 1;
	        Tests existingTest = new Tests();
	        existingTest.setTestId(1);
	        existingTest.setTestName("Existing Test");

	        DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
	        diagnosticCenter.setCenterId(centerId);
	        diagnosticCenter.setListOfTests(List.of(existingTest));

	        when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(diagnosticCenter));

	        // Call the service method with an existing test
	        Tests test = new Tests();
	        test.setTestId(2);
	        test.setTestName("Existing Test");

	        String result = testService.addTestToDiagnosticCenter(centerId, test);

	        // Assertions
	        assertEquals("Test with th same name already exists", result);
	        verify(diagnosticCenterRepository, times(1)).findById(centerId);
	        verifyNoMoreInteractions(testRepository);
	        verifyNoMoreInteractions(diagnosticCenterRepository);
	    }

	    
	    

	    @Test
	    void testRemoveTestFromDiagnosticCenter_TestNotPresent() throws TestException {
	        // Mocking data
	        int centerId = 1;
	        int testId = 2;

	        DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
	        diagnosticCenter.setCenterId(centerId);

	        when(diagnosticCenterRepository.findById(centerId)).thenReturn(Optional.of(diagnosticCenter));

	        // Call the service method with a test not present in the center
	        String result = testService.removeTestFromDiagnosticCenter(centerId, testId);

	        // Assertions
	        assertEquals("test not removed", result);
	        verify(diagnosticCenterRepository, times(1)).findById(centerId);
	        verifyNoMoreInteractions(testRepository);
	        verifyNoMoreInteractions(diagnosticCenterRepository);
	    }
	}


