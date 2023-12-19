package com.healthcaresystem.dto;

public class TestCenterInfoDTO {

	private int centerId;
	private int testId;
	private String centerName;
	private String testName;
	private String centerAddress;

	public int getCenterId() {
		return centerId;
	}

	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}

	public TestCenterInfoDTO(int centerId, int testId, String centerName, String testName, String centerAddress) {
		super();
		this.centerId = centerId;
		this.centerAddress = centerAddress;
		this.testId = testId;
		this.centerName = centerName;
		this.testName = testName;
	}

	public TestCenterInfoDTO() {
		super();
	}

}
