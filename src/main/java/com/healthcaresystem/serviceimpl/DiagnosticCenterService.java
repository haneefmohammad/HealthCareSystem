package com.healthcaresystem.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Test;
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

//	public String addDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
//		// Save the diagnostic center first to ensure it has an ID
//		DiagnosticCenter savedCenter = diagnosticCenterRepository.save(diagnosticCenter);
//
//		// Create default tests for the saved diagnostic center
//		List<Test> defaultTests = createDefaultTests(savedCenter);
//
//		// Set the diagnostic center for each test and save them
//		defaultTests.forEach(test -> {
//			test.setDiagnosticCenter(savedCenter);
//			testRepository.save(test);
//		});
//
//		return "Center added successfully";
//	}
//
//	private List<Test> createDefaultTests(DiagnosticCenter diagnosticCenter) {
//		List<Test> defaultTests = new ArrayList<>();
//
//		Test bloodGroupTest = new Test("Blood Group");
//		Test bloodSugarTest = new Test("Blood Sugar");
//		Test bloodPressureTest = new Test("Blood Pressure");
//
//		// Add tests to the list
//		defaultTests.add(bloodGroupTest);
//		defaultTests.add(bloodSugarTest);
//		defaultTests.add(bloodPressureTest);
//
//		return defaultTests;
//	}

//		System.out.println(diagnosticCenter.getCenterName());
//		diagnosticCenter.setListOfTests(getDefaultTests(diagnosticCenter));
//		diagnosticCenterRepository.save(diagnosticCenter);
//		return "center added successfully";
//	for(DiagnosticCenter center :diagnosticCenter)
//	{
//		System.out.println(center.getCenterName());
//		List<Test> defaultTest =getDefaultTests(center);
//		center.setListOfTests(defaultTest);
//	}
//	diagnosticCenterRepository.saveAll(diagnosticCenter);
//	return "center added succesfully";
	public String addDiagnosticCenter(DiagnosticCenter diagnosticCenter) {

		//List<Test> defaultTest = getDefaultTests(diagnosticCenter);
		List<Test> defaultTest = new ArrayList<>();

		Test test = testRepository.findByTestNameIgnoreCase("Blood Group");

		if (test == null) {
			Test bloodGroupTest = new Test();
			bloodGroupTest.setTestName("Blood Group");
			defaultTest.add(bloodGroupTest);
		} else {
			defaultTest.add(test);
		}

		test = testRepository.findByTestNameIgnoreCase("Blood Sugar");

		if (test == null) {
			Test bloodGroupTest = new Test();
			bloodGroupTest.setTestName("Blood Sugar");
			defaultTest.add(bloodGroupTest);
		} else {
			defaultTest.add(test);
		}

		test = testRepository.findByTestNameIgnoreCase("Blood Pressure");

		if (test == null) {
			Test bloodGroupTest = new Test();
			bloodGroupTest.setTestName("Blood Pressure");
			defaultTest.add(bloodGroupTest);
		} else {
			defaultTest.add(test);
		}

		diagnosticCenter.setListOfTests(defaultTest);
		diagnosticCenterRepository.save(diagnosticCenter);
		return "Center added successfully";
	}

//	private List<Test> getDefaultTests(DiagnosticCenter diagnosticCenter) {
//
//		Test bloodSugarTest = new Test();
//		bloodSugarTest.setTestName("Blood Sugar");
//		// bloodSugarTest.setDiagnosticCenter(diagnosticCenter);
//
//		Test bloodPressureTest = new Test();
//		bloodPressureTest.setTestName("Blood Pressure");
//		// bloodPressureTest.setDiagnosticCenter(diagnosticCenter);
//
//		defaultTests.add(bloodPressureTest);
//		defaultTests.add(bloodSugarTest);
//		defaultTests.add(bloodGroupTest);
////
//		testRepository.save(bloodGroupTest);
//		testRepository.save(bloodPressureTest);
//		testRepository.save(bloodSugarTest);
//
//		// return List.of(bloodGroupTest, bloodSugarTest, bloodPressureTest);
//		testRepository.saveAll(defaultTests);
//		return defaultTests;
//	}

	public List<DiagnosticCenter> getAllCenters() {
		return diagnosticCenterRepository.findAll();
	}

	public String removeCenter(int centerId) {
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepository.findById(centerId)
				.orElseThrow(() -> new DiagnosticCenterNotFoundException("Center not found with id"));
		diagnosticCenter.setAppointmentList(new ArrayList<>());
		diagnosticCenterRepository.save(diagnosticCenter);
		diagnosticCenterRepository.delete(diagnosticCenter);

		return "center removed";
	}

}
