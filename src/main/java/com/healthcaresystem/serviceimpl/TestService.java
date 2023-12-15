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
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;

import jakarta.transaction.Transactional;

@Service
public class TestService {

	
	@Autowired
	TestRepository testRepository;
	
	@Autowired
	DiagnosticCenterRepository diagnosticCenterRepository;
	
	public TestService(DiagnosticCenterRepository diagnosticCenterRepository)
	{
		this.diagnosticCenterRepository = diagnosticCenterRepository;
	}

	 public Optional<DiagnosticCenter> getDiagnosticCentersByCenterId(int centerId) {
	        return diagnosticCenterRepository.findByCenterId(centerId);
	    }   
	
	    @Transactional
	    public String addTestToDiagnosticCenter(int centerId, Test test) throws DiagnosticCenterException {
	        // Find the diagnostic center by ID
	        Optional<DiagnosticCenter> optionalCenter = diagnosticCenterRepository.findByCenterId(centerId);
	
	        if (optionalCenter.isPresent()) {
	            DiagnosticCenter diagnosticCenter = optionalCenter.get();
	
	            // Set the diagnostic center for the test
	            test.setDiagnosticCenter(diagnosticCenter);
	
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
	    public void removeTestFromDiagnosticCenter(int centerId, int testId)  throws TestException{

	        Optional<DiagnosticCenter> optionalDiagnosticCenter = diagnosticCenterRepository.findByCenterId(centerId);
	              	if (optionalDiagnosticCenter.isPresent()) {
	        	    optionalDiagnosticCenter.ifPresent(diagnosticCenter -> {
	        	        Test testToRemove = diagnosticCenter.getListOfTests()
	        	                .stream().filter(test -> test.getTestId() == testId)
	        	                .findFirst().orElseThrow(() -> new TestException("test not found") );

	        	        // Remove the test and its details
	        	        diagnosticCenter.getListOfTests().remove(testToRemove);
	        	        diagnosticCenterRepository.save(diagnosticCenter);
	        	        testRepository.deleteById((int) testId);

	        	    });
	        	} else {
	        	    System.out.println("Diagnostic Center not found with ID: " + centerId);
	        	}
	}
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

