package com.healthcaresystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
	

	//private int userId;
	@NotBlank(message = "UserName is Required")
	private String userName;
	@NotBlank(message = "User Password is Rerquired")
	private String userPassword;
	
	@Pattern(regexp = "\\d{10}")
	private long phoneNumber;
	private String gender;
	private int age;
	private String userRole;
	@NotBlank(message="Email is required")
	@Email(message = "Please provide a valid Emial")
	private String userEmail;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
//	public int getUserId() {
//		return userId;
//	}
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public UserDTO( String userName, String userPassword, long phoneNumber, String gender, int age,
			String userRole, String userEmail) {
		super();
		//this.userId = userId;int userId,
		this.userName = userName;
		this.userPassword = userPassword;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.age = age;
		this.userRole = userRole;
		this.userEmail=userEmail;
	}
	public UserDTO() {
		super();
	}
	
	

}
