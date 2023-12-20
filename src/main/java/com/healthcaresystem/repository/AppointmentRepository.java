package com.healthcaresystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcaresystem.entity.Appointment;
import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Tests;
import com.healthcaresystem.entity.User;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>
{
	List<Appointment> findByApprovedFalse();
	boolean existsByUserAndTestAndDiagnosticCenterAndDateTime(User user, Tests test, DiagnosticCenter diagnosticCenter,
			LocalDateTime appointmentDate);
	
	//boolean existsByUserandDateTime(User user, LocalDateTime dateTime);
}
