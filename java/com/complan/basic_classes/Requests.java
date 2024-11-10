package com.complan.basic_classes;

import java.time.LocalDateTime;

public class Requests {
	private String from;
	private String to;
	private String type;
	private LocalDateTime timeOfRequest;
	private boolean isAccepted = false;
	private int SlotID;
	private int requestId;

	public Requests(String from, String to, String type, LocalDateTime timeOfRequest, int requestId, int SlotID) {
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

	public int getRequestId() {
		return requestId;
	}

	public int getSlotId() {
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

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public void setTimeOfRequest(LocalDateTime timeOfRequest) {
		this.timeOfRequest = timeOfRequest;
	}

	public void setSlotId(int SlotId) {
		this.SlotID = SlotId;
	}
}
