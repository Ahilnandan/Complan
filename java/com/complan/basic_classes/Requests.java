package com.complan.basic_classes;

import java.time.LocalDateTime;

public class Requests {
	private String from;
	private String to;
	private String type;
	private LocalDateTime timeOfRequest;
	private boolean isAccepted;
	private boolean responded;
	private String SlotID;
	private String requestId;
	private static int RequestCount = 0;

	public Requests(String from, String to, String type, LocalDateTime timeOfRequest, String requestId, String SlotID, boolean acc, boolean res) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.timeOfRequest = timeOfRequest;
		this.requestId = requestId;
		this.SlotID = SlotID;
		this.isAccepted = acc;
		this.responded = res;
	}

	public Requests(String from, String to, String type, LocalDateTime timeOfRequest, String requestId, String SlotID) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.timeOfRequest = timeOfRequest;
		this.requestId = requestId;
		this.SlotID = SlotID;
		this.isAccepted = false;
		this.responded = false;
	}


	public boolean isResponded(){
		return responded;
	}
	
	public void setResponded(boolean res){
		this.responded = res;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getSlotID() {
		return SlotID;
	}

	public void setSlotID(String slotID) {
		SlotID = slotID;
	}

	public static int getRequestCount() {
		return RequestCount;
	}

	public static void setRequestCount(int requestCount) {
		RequestCount = requestCount;
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
