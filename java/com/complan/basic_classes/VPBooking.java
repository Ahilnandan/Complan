package com.complan.basic_classes;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VPBooking {
	private User Owner;
	private LocalDateTime departureTime;
	private String fromLocation;
	private String toLocation;
	private ArrayList<User> Partners;
	private String VPID;

	public VPBooking(User Owner, LocalDateTime departureTime, String fromLocation, String toLocation, String VPID) {
		this.Owner = Owner;
		this.departureTime = departureTime;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.VPID = VPID;
		Partners = new ArrayList<User>();
	}

	public void addPartner(User hi) {
		Partners.add(hi);
	}

	public void removePartner(User bye) {
		for (int i = 0; i < Partners.size(); i++) {
			if (Partners.get(i).sameUser(bye)) {
				Partners.remove(i);
			}
		}
	}

	public User getOwner() {
		return Owner;
	}

	public LocalDateTime getDeparture() {
		return departureTime;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public ArrayList<User> getPartners() {
		return Partners;
	}

	public String getVPID() {
		return VPID;
	}

	public void setOwner(User Thala) {
		Owner = Thala;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public void setVPID(String VPID) {
		this.VPID = VPID;
	}
}
