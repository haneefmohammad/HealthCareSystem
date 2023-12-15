package com.healthcaresystem.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.healthcaresystem.entity.DiagnosticCenter;
import com.healthcaresystem.entity.Test;
import com.healthcaresystem.entity.User;

public class MakeAppointmentDTO {

	
	private int userId;
	private int centerId;
	private List<Test> testIds;
	private LocalDateTime dateTime;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCenterId() {
		return centerId;
	}
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}
	public List<Test> getTestIds() {
		return testIds;
	}
	public void setTestId(List<Test> testIds) {
		this.testIds = testIds;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public MakeAppointmentDTO(int userId, int centerId, List<Test> testIds, LocalDateTime dateTime) {
		super();
		this.userId = userId;
		this.centerId = centerId;
		this.testIds = testIds;
		this.dateTime = dateTime;
	}
	public MakeAppointmentDTO() {
		super();
	}
	@Override
	public String toString() {
		return "MakeAppointmentDTO [userId=" + userId + ", centerId=" + centerId + ", testId=" + testIds + ", dateTime="
				+ dateTime + "]";
	}
	
	
	
	
}
