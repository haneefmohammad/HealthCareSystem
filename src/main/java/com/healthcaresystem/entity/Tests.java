package com.healthcaresystem.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "testId")
public class Tests {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="test_id")
	private int testId;
	
	@Column(name="test_name")
	private String testName;
	

	@ManyToMany(mappedBy = "listOfTests",cascade = CascadeType.ALL)
    private  List<DiagnosticCenter> diagnosticCenter = new ArrayList<>();
	
	@OneToMany(mappedBy ="test" ,cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Appointment> appointment;
	

	

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

	public List<DiagnosticCenter> getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(List<DiagnosticCenter> diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}

	public List<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<Appointment> appointment) {
		this.appointment = appointment;
	}

	public Tests(int testId, String testName, List<DiagnosticCenter> diagnosticCenter) {
		super();
		this.testId = testId;
		this.testName = testName;
		this.diagnosticCenter = diagnosticCenter;
	}
	
	public Tests( String testName, List<DiagnosticCenter> diagnosticCenter) {
		super();
		
		this.testName = testName;
		this.diagnosticCenter = diagnosticCenter;
	}
	

	public Tests(int testId, String testName, List<DiagnosticCenter> diagnosticCenter, List<Appointment> appointment) {
		super();
		this.testId = testId;
		this.testName = testName;
		this.diagnosticCenter = diagnosticCenter;
		this.appointment = appointment;
	}

	public Tests( String testName) {
		super();
		
		this.testName = testName;
	}
	

	public Tests() {
		super();
	}

	
}
