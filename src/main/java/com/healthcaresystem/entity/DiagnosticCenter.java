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
	
	@Column(name="center_name")
	private String centerName;
	
	@OneToMany(mappedBy = "diagnosticCenter")
	//@JsonBackReference(value= "diagnostic_center")
	private List<Appointment> appointmentList=new ArrayList<>();
	
	@OneToMany(mappedBy = "diagnosticCenter",cascade = CascadeType.ALL)
	private List<Test> listOfTests = new ArrayList<>();
	
//	
//	 @OneToMany(mappedBy = "diagnosticCenter") 
//	 private List<User> userList;
//	
	 
	
	/*
	 * @OneToMany(mappedBy = "center_list") private List<User> users;
	 */

	/*
	 * public List<User> getUsers() { return users; }
	 * 
	 * public void setUsers(List<User> users) { this.users = users; }
	 */

//	public List<User> getUserList() {
//		return userList;
//	}
//
//	public void setUserList(List<User> userList) {
//		this.userList = userList;
	//}
//
//	public List<User> getUserList() {
//		return userList;
//	}
//
//	public void setUserList(List<User> userList) {
//		this.userList = userList;
//	}

	public int getCenterId() {
		return centerId;
	}

	public void setCenteId(int centreId) {
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

	public List<Test> getListOfTests() {
		return listOfTests;
	}

	public void setListOfTests(List<Test> listOfTests) {
		this.listOfTests = listOfTests;
	}

	

	
	public DiagnosticCenter(int centerId, String centerName, List<Appointment> appointmentList,
			List<Test> listOfTests) {
		super();
		this.centerId = centerId;
		this.centerName = centerName;
		this.appointmentList = appointmentList;
		this.listOfTests = listOfTests;
		//this.userList = userList;, List<User> userList
	}

	public DiagnosticCenter() {
		super();
	}
	
}
