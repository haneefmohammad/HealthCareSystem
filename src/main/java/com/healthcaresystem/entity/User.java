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

//	 	@Column(name="user_list")
//	    private List<User> userList;

	/*
	 * @Column(name = "user_list") private HashMap<String, User> userList;
	 */

	/*
	 * @OneToMany(mappedBy = "users") private List<DiagnosticCenter> center_list;
	 */
	/*
	 * @OneToOne // @JoinColumn(name="") private List<Appointment> appointmentList;
	 */
	@ManyToOne
	@JoinColumn(name = "centre_id")
	private DiagnosticCenter diagnosticCenter;

	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}

	/*
	 * public List<DiagnosticCenter> getCenter_list() { return center_list; }
	 * 
	 * public void setCenter_list(List<DiagnosticCenter> center_list) {
	 * this.center_list = center_list; }
	 */

//	public List<Appointment> getAppointmentList() {
//		return appointmentList;
//	}
//
//	public void setAppointmentList(List<Appointment> appointmentList) {
//		this.appointmentList = appointmentList;
//	}

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
	/*
	 * public HashMap<String, User> getUserList() { return userList; }
	 * 
	 * public void setUserList(HashMap<String, User> userList) { this.userList =
	 * userList; }
	 */

	/*
	 * public List<DiagnosticCenter> getCenterList() { return center_list; }
	 * 
	 * public void setCenterList(List<DiagnosticCenter> centerList) {
	 * this.center_list = centerList; }
	 * 
	 */

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
		// this.userList = userList;
		// this.center_list = center_list;
		// this.appointmentList = appointmentList; List<Appointment> appointmentList,
		this.diagnosticCenter = diagnosticCenter;
		this.appointment = appointment;
	}

	public User() {
		super();
	}

	public static class UserBuilder {
		private int userId;
		private String userName;
		private String userPassword;
		private long phoneNumber;
		private String gender;
		private int age;
		private String userRole;
		private String userEmail;

		public UserBuilder(String userName, String userPassword) {
			this.userName = userName;
			this.userPassword = userPassword;
		}
		public UserBuilder userId(int userId)
		{
			this.userId = userId;
			return this;
		}

		public UserBuilder userEmail(String userEmail) {
			this.userEmail = userEmail;
			return this;
		}

		public UserBuilder phoneNumber(long phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserBuilder age(int age) {
			this.age = age;
			return this;
		}

		public UserBuilder gender(String gender) {
			this.gender = gender;
			return this;
		}

		public UserBuilder userRole(String userRole) {
			this.userRole = userRole;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

	private User(UserBuilder builder) {
		this.userId = builder.userId;
		this.userName = builder.userName;
		this.userPassword = builder.userPassword;
		this.userEmail = builder.userEmail;
		this.phoneNumber = builder.phoneNumber;
		this.age = builder.age;
		this.gender = builder.gender;
		this.userRole = builder.userRole;
	}

}
