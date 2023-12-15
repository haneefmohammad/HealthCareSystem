package com.healthcaresystem.iservice;

import java.util.List;

import com.healthcaresystem.entity.DiagnosticCenter;

public interface DiagnosticCenterInterface {

	DiagnosticCenter addDiagnosticCenter(DiagnosticCenter diagnosticCenter);
	
	List<DiagnosticCenter> getAllCenters();
	
	String removeCenter(Integer centerId);
	
	
	
}
