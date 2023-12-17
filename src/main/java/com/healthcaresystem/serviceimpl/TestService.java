package com.healthcaresystem.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Test;
import com.healthcaresystem.exception.DiagnosticCenterException;
import com.healthcaresystem.exception.TestException;
import com.healthcaresystem.exception.TestNotFoundException;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;

import jakarta.transaction.Transactional;

@Service
public class TestService {

	@Autowired
	TestRepository testRepository;

	@Autowired
	DiagnosticCenterRepository diagnosticCenterRepository;

	public TestService(DiagnosticCenterRepository diagnosticCenterRepository) {
		this.diagnosticCenterRepository = diagnosticCenterRepository;
	}

	public Optional<DiagnosticCenter> getDiagnosticCentersByCenterId(int centerId) {
		return diagnosticCenterRepository.findById(centerId);
	}

	@Transactional
	public String addTestToDiagnosticCenter(int centerId, Test test) throws DiagnosticCenterException {
		// Find the diagnostic center by ID
		Optional<DiagnosticCenter> optionalCenter = diagnosticCenterRepository.findById(centerId);

		if (optionalCenter.isPresent()) {
			DiagnosticCenter diagnosticCenter = optionalCenter.get();

			boolean testExists = diagnosticCenter.getListOfTests().stream()
					.anyMatch(existingTest -> existingTest.getTestName().equals(test.getTestName()));

			if (testExists) {
				return "Test with th same name already exists";
			}
			// Set the diagnostic center for the test
			test.getDiagnosticCenter().add(diagnosticCenter);

			// Add the test to the diagnostic center's list of tests
			diagnosticCenter.getListOfTests().add(test);

			// Save changes to both entities
			diagnosticCenterRepository.save(diagnosticCenter);
			testRepository.save(test);

			return "Test added to Diagnostic Center successfully.";
		} else {
			return "Diagnostic Center not found with ID: " + centerId;
		}
	}

	
public String removeTestFromDiagnosticCenter(int centerId, int testId) throws TestException {
	    Optional<DiagnosticCenter> optionalDiagnosticCenter = diagnosticCenterRepository.findById(centerId);
	    if (optionalDiagnosticCenter.isPresent()) {
	        DiagnosticCenter diagnosticCenter = optionalDiagnosticCenter.get();
	        System.out.println("inside dwrgc");
	        Optional<Test> existingTest = diagnosticCenter.getListOfTests().stream()
	                .filter(test -> test.getTestId() == testId)
	                .findFirst();

	        if (existingTest.isPresent()) {
	            Test test = existingTest.get();
	            System.out.println("sRgarw");
	            diagnosticCenter.getListOfTests().remove(test); // Remove the test from the diagnostic center
	            test.getDiagnosticCenter().remove(diagnosticCenter); // Remove the diagnostic center from the test
	            testRepository.save(test);
	            diagnosticCenterRepository.save(diagnosticCenter);
	            return "Test removed";
	           //return true;
	           // throw new RuntimeException("test removed");
	            
	        }
	    }
	    
	    return "test not removed";
	}

  /* public String removeTestFromDiagnosticCenter(int centerId, int testId) throws TestException {

		Optional<DiagnosticCenter> optionalDiagnosticCenter = diagnosticCenterRepository.findById(centerId);
		if (optionalDiagnosticCenter.isPresent()) {
			System.out.println("hi from inside");
			DiagnosticCenter diagnosticCenter = optionalDiagnosticCenter.get();
			Optional<Test> existingTest = diagnosticCenter.getListOfTests().stream()
 				.filter(test -> test.getTestId()== testId).findFirst();
   			if(existingTest.isPresent()) {
   				System.out.println("from existing test");
   				Test test = existingTest.get();
   				diagnosticCenter.getListOfTests().remove(test);
   				test.getDiagnosticCenter().remove(diagnosticCenter);
   				return "Test removed";
   			}
		}
		 System.out.println("diagnostic center not found");
			return "Diagnostic Center not found with ID: " + centerId;	
	}   */
}

//	
//	public String addDefaultTestforCenter(DiagnosticCenter diagnosticCenter)
//	{
//		List<Test> defaultTests = createDefaultTests(diagnosticCenter);
//        testRepository.saveAll(defaultTests);
//
//        return "Default tests added for center: " + diagnosticCenter.getCentreName();
//    }
//
//    private List<Test> createDefaultTests(DiagnosticCenter diagnosticCenter) {
//        List<Test> defaultTests = new ArrayList<>();
//        Test bloodGroupTest = new Test("Blood Group", diagnosticCenter);
//        Test bloodSugarTest = new Test("Blood Sugar", diagnosticCenter);
//        Test bloodPressureTest = new Test("Blood Pressure", diagnosticCenter);
//
//        defaultTests.add(bloodGroupTest);
//        defaultTests.add(bloodSugarTest);
//        defaultTests.add(bloodPressureTest);
//
//        return defaultTests;
//    }
