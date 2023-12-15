package com.healthcaresystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcaresystem.entity.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>
{
	List<Appointment> findByApprovedFalse();
}
