package com.healthcaresystem.iservice;

import java.util.List;

import com.healthcaresystem.entity.Appointment;

public interface Appointments {

	List<Appointment> getPendingAppointments();

	void approveAppointment(int appointmentId, int centerId);

}
