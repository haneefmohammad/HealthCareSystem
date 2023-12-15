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
	//@JsonManagedReference(value= "diagnostic_center")
	private DiagnosticCenter diagnosticCenter;
	
	@OneToOne
	@JoinColumn(name = "user_id")			
	private User user;
	
	@OneToMany(mappedBy = "appointment",cascade = CascadeType.ALL)
	private List<Test> listOfTests =new ArrayList<>();

	
	
	public Appointment() {
		super();
	}
//
//	public Test getListOfTests() {
//		return listOfTests;
//	}
//
//	public void setListOfTests(Test test) {
//		this.listOfTests = test;
//	}

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

	public List<Test> getlistOfTests() {
		return listOfTests;
	}

	public void setlistOfTests(List<Test> listOfTests) {
		this.listOfTests = listOfTests;
	}

	public Appointment(int appointmentId, LocalDateTime dateTime, Boolean approved,
			DiagnosticCenter diagnosticCenter, User user, List<Test> listOfTests) {
		super();
		this.appointmentId = appointmentId;
		this.dateTime = dateTime;
		this.approved = approved;
		this.diagnosticCenter = diagnosticCenter;
		this.user = user;
		this.listOfTests = listOfTests;
	}

}
