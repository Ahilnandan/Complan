package com.complan.basic_classes;

import java.time.LocalDateTime;
import java.util.Vector;

public class VPBooking {
	private User Owner;
	private LocalDateTime departureTime;
	private String fromLocation;
	private String toLocation;
	private Vector<User> Partners;
	private String VPID;
	private static int VPSlotCount = 0;

	public VPBooking(User Owner, LocalDateTime departureTime, String fromLocation, String toLocation, String VPID) {
		this.Owner = Owner;
		this.departureTime = departureTime;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.VPID = VPID;
		Partners = new Vector<User>();
		VPSlotCount++;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setPartners(Vector<User> partners) {
		Partners = partners;
	}

	public static int getVPSlotCount() {
		return VPSlotCount;
	}

	public static void setVPSlotCount(int vPSlotCount) {
		VPSlotCount = vPSlotCount;
	}

	public void addPartner(User hi) {
		Partners.add(hi);
	}

	public boolean PartnerInSlot(User partner) {
		for (int i = 0; i < Partners.size(); i++) {
			if (Partners.get(i).equals(partner)) {
				return true;
			}
		}
		return false;
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

	public Vector<User> getPartners() {
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
