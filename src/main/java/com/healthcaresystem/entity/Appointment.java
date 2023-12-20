package com.healthcaresystem.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appointmentId")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="appointment_id")
	private int appointmentId;
	
	@Column(name="dateTime")
	private LocalDateTime dateTime;
	
	@Column(name="approved")
	private boolean approved;
	
	@ManyToOne
	@JoinColumn(name = "center_id")
	private DiagnosticCenter diagnosticCenter;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")			
	private User user;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Tests test;

	
	
	public Appointment() {
		super();
	}


	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tests getTest() {
		return test;
	}

	public void setTest(Tests test) {
		this.test = test;
	}
	public Appointment(int appointmentId, LocalDateTime dateTime, boolean approved, DiagnosticCenter diagnosticCenter,
			User user, Tests test) {
		super();
		this.appointmentId = appointmentId;
		this.dateTime = dateTime;
		this.approved = approved;
		this.diagnosticCenter = diagnosticCenter;
		this.user = user;
		this.test = test;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", dateTime=" + dateTime + ", approved=" + approved
				+ ", diagnosticCenter=" + diagnosticCenter + ", user=" + user + ", test=" + test + "]";
	}


	

}
