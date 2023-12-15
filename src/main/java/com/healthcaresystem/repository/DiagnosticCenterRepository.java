package com.healthcaresystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcaresystem.entity.DiagnosticCenter;
@Repository
public interface DiagnosticCenterRepository extends JpaRepository<DiagnosticCenter, String> {

	Optional<DiagnosticCenter> findByCenterId(int centreId);
	
	Optional<DiagnosticCenter> getDiagnosticCentersByCenterId(int centerId);
	
}
