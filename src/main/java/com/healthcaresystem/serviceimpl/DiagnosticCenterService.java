package com.healthcaresystem.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcaresystem.dto.TestCenterInfoDTO;
import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.exception.DiagnosticCenterNotFoundException;
import com.healthcaresystem.repository.DiagnosticCenterRepository;
import com.healthcaresystem.repository.TestRepository;

@Service
@Transactional
public class DiagnosticCenterService {

	@Autowired
	DiagnosticCenterRepository diagnosticCenterRepository;

	@Autowired
	TestRepository testRepository;


	public String addDiagnosticCenter(DiagnosticCenter diagnosticCenter) {

		List<Tests> defaultTest = new ArrayList<>();

		Tests test = testRepository.findByTestNameIgnoreCase("Blood Group");

		if (test == null) {
			Tests bloodGroupTest = new Tests();
			bloodGroupTest.setTestName("Blood Group");
			defaultTest.add(bloodGroupTest);
		} else {
			defaultTest.add(test);
		}

		test = testRepository.findByTestNameIgnoreCase("Blood Sugar");

		if (test == null) {
			Tests bloodGroupTest = new Tests();
			bloodGroupTest.setTestName("Blood Sugar");
			defaultTest.add(bloodGroupTest);
		} else {
			defaultTest.add(test);
		}

		test = testRepository.findByTestNameIgnoreCase("Blood Pressure");

		if (test == null) {
			Tests bloodGroupTest = new Tests();
			bloodGroupTest.setTestName("Blood Pressure");
			defaultTest.add(bloodGroupTest);
		} else {
			defaultTest.add(test);
		}

		diagnosticCenter.setListOfTests(defaultTest);
		diagnosticCenterRepository.save(diagnosticCenter);
		return "Center added successfully";
	}



	public List<DiagnosticCenter> getAllCenters() {
		return diagnosticCenterRepository.findAll();
	}

//	public String removeCenter(int centerId) {
//		DiagnosticCenter diagnosticCenter = diagnosticCenterRepository.findById(centerId)
//				.orElseThrow(() -> new DiagnosticCenterNotFoundException("Center not found with id"));
//		diagnosticCenter.setAppointmentList(new ArrayList<>());
//		diagnosticCenterRepository.save(diagnosticCenter);
//		diagnosticCenterRepository.delete(diagnosticCenter);
//
//		return "center removed";
//	}

	public String removeCenter(int centerId) {
	    DiagnosticCenter diagnosticCenter = diagnosticCenterRepository.findById(centerId)
	            .orElseThrow(() -> new DiagnosticCenterNotFoundException("Center not found with id"));

	    // Remove associated tests
	    diagnosticCenter.setListOfTests(Collections.emptyList()); // Assuming the removal of all tests associated with the center

	    // Remove appointments or any other related data

	    diagnosticCenterRepository.delete(diagnosticCenter);

	    return "center removed";
	}
	
	
	public List<TestCenterInfoDTO> getTestCenterInfo()
	{
		List<TestCenterInfoDTO> testCenterInfoList =  new ArrayList<>();
		
		List<DiagnosticCenter> diagnosticCenters = diagnosticCenterRepository.findAll();
		for(DiagnosticCenter diagnosticCenter: diagnosticCenters)
		{
			int centerId = diagnosticCenter.getCenterId();
			String centerName = diagnosticCenter.getCenterName();
			String centerAddress = diagnosticCenter.getCenterAddress();
			
			List<Tests> tests = diagnosticCenter.getListOfTests();
			for(Tests test: tests) {
				int testID = test.getTestId();
				String testName = test.getTestName();
				
				TestCenterInfoDTO testCenterInfoDTO = new TestCenterInfoDTO(centerId, testID, centerName, testName ,centerAddress);
				testCenterInfoList.add(testCenterInfoDTO);
			}
		}
		return testCenterInfoList;
	

		
	}
	
}
