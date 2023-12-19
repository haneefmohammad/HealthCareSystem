package com.healthcaresystem.dto;

public class AppointmentDetailsDTO {
	
	private int appointmentId;
	private int userID;
	private int testId;
	private String testName;
	private int centerId;
	private String centerName;
	private boolean approved;
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
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
	public int getCenterId() {
		return centerId;
	}
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}
	public String getCenterName() {
		return centerName;
	}
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	
	public AppointmentDetailsDTO(int appointmentId, int userID, int testId, String testName, int centerId,
			String centerName, boolean approved) {
		super();
		this.appointmentId = appointmentId;
		this.userID = userID;
		this.testId = testId;
		this.testName = testName;
		this.centerId = centerId;
		this.centerName = centerName;
		this.approved = approved;
	}
	public AppointmentDetailsDTO() {
		super();
	}
	
		
	}

	

