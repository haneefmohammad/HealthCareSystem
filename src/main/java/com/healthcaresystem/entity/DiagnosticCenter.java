package com.healthcaresystem.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnostic_center")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "centerId")
public class DiagnosticCenter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "center_id")
	private int centerId;

	@Column(name = "center_name")
	private String centerName;
	@Column(name="center_address")
	private String centerAddress;
	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}

	@OneToMany(mappedBy = "diagnosticCenter")
	
	private List<Appointment> appointmentList = new ArrayList<>();

	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "center_test", joinColumns = @JoinColumn(name = "center_id"), inverseJoinColumns = @JoinColumn(name = "test_id"))
	private List<Tests> listOfTests = new ArrayList<>();


	public int getCenterId() {
		return centerId;
	}

	@Override
	public String toString() {
		return "DiagnosticCenter [centerId=" + centerId + ", centerName=" + centerName + ", appointmentList="
				+ appointmentList + ", listOfTests=" + listOfTests.size() + "]";
	}

	public void setCenterId(int centreId) {
		this.centerId = centreId;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centreName) {
		this.centerName = centreName;
	}

	public List<Appointment> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}

	public List<Tests> getListOfTests() {
		return listOfTests;
	}

	public void setListOfTests(List<Tests> listOfTests) {
		this.listOfTests = listOfTests;
	}

	public DiagnosticCenter(int centerId, String centerName, List<Appointment> appointmentList,
			List<Tests> listOfTests,String centerAddress) {
		super();
		this.centerAddress=centerAddress;
		this.centerId = centerId;
		this.centerName = centerName;
		this.appointmentList = appointmentList;
		this.listOfTests = listOfTests;
		
	}

	public DiagnosticCenter() {
		super();
	}

}
