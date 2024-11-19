package com.complan.basic_classes;

import java.time.LocalDateTime;

public class WMSlot {
	private User user;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int OTP;
	private String slotId;

	public WMSlot(User user, LocalDateTime startTime, LocalDateTime endTime, int OTP, String slotId) {
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.OTP = OTP;
		this.slotId = slotId;
	}

	public User getUser() {
		return user;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public int getOTP() {
		return OTP;
	}

	public String getSlotId() {
		return slotId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setOTP(int OTP) {
		this.OTP = OTP;
	}

	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}
}
