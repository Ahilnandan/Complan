package com.complan.basic_classes;

import java.time.LocalDateTime;

public class Requests {
	private String from;
	private String to;
	private String type;
	private LocalDateTime timeOfRequest;
	private boolean isAccepted = false;
	private String SlotID;
	private String requestId;

	public Requests(String from, String to, String type, LocalDateTime timeOfRequest, String requestId, String SlotID) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.timeOfRequest = timeOfRequest;
		this.requestId = requestId;
		this.SlotID = SlotID;
		this.isAccepted = false;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getType() {
		return type;
	}

	public LocalDateTime getTimeOfRequest() {
		return timeOfRequest;
	}

	public boolean getAccept() {
		return isAccepted;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getSlotId() {
		return SlotID;
	}

	public void setAccept(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setTimeOfRequest(LocalDateTime timeOfRequest) {
		this.timeOfRequest = timeOfRequest;
	}

	public void setSlotId(String SlotId) {
		this.SlotID = SlotId;
	}
}
