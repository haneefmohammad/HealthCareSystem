package com.healthcaresystem.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "user_tbl")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "user_password")
	private String userPassword;

	@Column(name = "phone_number", length = 10)
	private long phoneNumber;

	@Column(name = "user_role")
	private String userRole;
	
	@Column(name = "user_email", unique = true)
	private String userEmail;

	@Column(name = "age")
	private int age;

	@OneToOne(cascade = CascadeType.ALL)
	private Appointment appointment;

	@Column(name = "gender")
	private String gender;


	@ManyToOne
	@JoinColumn(name = "centre_id")
	private DiagnosticCenter diagnosticCenter;

	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}

	

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public User(int userId, String userName, String userPassword, long phoneNumber, String userRole, String userEmail,
			int age, String gender, DiagnosticCenter diagnosticCenter, Appointment appointment) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.phoneNumber = phoneNumber;
		this.userRole = userRole;
		this.userEmail = userEmail;
		this.age = age;
		this.gender = gender;
		this.diagnosticCenter = diagnosticCenter;
		this.appointment = appointment;
	}

	public User() {
		super();
	}


}
