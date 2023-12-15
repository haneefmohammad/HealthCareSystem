package com.healthcaresystem.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "testId")
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="test_id")
	private int testId;
	
	@Column(name="test_name")
	private String testName;
	
	@ManyToOne
	@JoinColumn(name = "center_id")
    private DiagnosticCenter diagnosticCenter;
	
	@ManyToOne
	@JoinColumn(name = "appointment_id")
	@JsonIgnore
    private Appointment appointment;
	

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	

	public Test(int testId, String testName, DiagnosticCenter diagnosticCenter) {
		super();
		this.testId = testId;
		this.testName = testName;
		this.diagnosticCenter = diagnosticCenter;
	}
	
	public Test( String testName, DiagnosticCenter diagnosticCenter) {
		super();
		
		this.testName = testName;
		this.diagnosticCenter = diagnosticCenter;
	}
	

	public Test( String testName) {
		super();
		
		this.testName = testName;
	}
	

	public Test() {
		super();
	}

	
}
