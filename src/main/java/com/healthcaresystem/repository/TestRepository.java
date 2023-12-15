package com.healthcaresystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

	//List<Test> findByCenter(String centreName);

	 List<Test> findByDiagnosticCenter(DiagnosticCenter diagnosticCenter);
	//List<Test> findByCenterAndTestName(DiagnosticCenter diagnosticCenter, String testName);
	
	//List<Test> findByCenterId(int centreId);
	
	
	

	//List<Test> saveAll(List<Test> defaultTests);

}
