package com.healthcaresystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;

@Repository
public interface TestRepository extends JpaRepository<Tests, Integer> {

	//List<Test> findByCenter(String centreName);

	List<Tests> findByDiagnosticCenter(DiagnosticCenter diagnosticCenter);
	//List<Test> findByCenterAndTestName(DiagnosticCenter diagnosticCenter, String testName);
	
	//List<Test> findByCenterId(int centreId);
	Tests findByTestNameIgnoreCase(String testName);
	
	
	

	//List<Test> saveAll(List<Test> defaultTests);

}
