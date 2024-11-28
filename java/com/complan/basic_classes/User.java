package com.complan.basic_classes;

public class User {
	private String name;
	private int credits;
	private int points;
	private String emailId;
	private long MobileNumber;
	private boolean isAdmin;
	private String userId;
	private String password;
	private int cms;
	private static int UserCount = 0;

	public User(String name, String emailId,long MobileNumber, String password, String userId, int credits, int points, int cms) {
		this.name = name;
		this.emailId = emailId;
		this.MobileNumber = MobileNumber;
		this.userId = userId;
		this.password = password;
		this.isAdmin = false;
		this.credits = credits;
		this.points = points;
		this.cms = cms;
		//UserCount++;
	}

	public User() {

	}

	public User(String name, String emailId, long MobileNumber, String password, String userId) {
		this.name = name;
		this.emailId = emailId;
		this.MobileNumber = MobileNumber;
		this.userId = userId;
		this.password = password;
		this.isAdmin = false;
		this.credits = 12;
		this.points = 0;
	}

	public boolean sameUser(User dupe) {
		return ((dupe.getName() == this.name) && (dupe.getEmail() == this.emailId)
				&& (dupe.getUserID() == this.userId));
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCms() {
		return cms;
	}

	public void setCms(int cms) {
		this.cms = cms;
	}

	public void copy(User u) {
		this.name = u.getName();
		this.emailId = u.getEmail();
		this.MobileNumber = u.getMobileNumber();
		this.userId = u.getUserID();
		this.points = u.getPoints();
		this.credits = u.getCredits();
		this.password = u.getPassword();
		this.isAdmin = u.getAdmin();
	}

	public String getName() {
		return name;
	}

	public int getCredits() {
		return credits;
	}

	public int getPoints() {
		return points;
	}

	public String getEmail() {
		return emailId;
	}

	public long getMobileNumber() {
		return MobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public String getUserID() {
		return userId;
	}

	public boolean getAdmin() {
		return isAdmin;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setEmail(String emailId) {
		this.emailId = emailId;
	}

	public void setMobileNumber(long MobileNumber) {
		this.MobileNumber = MobileNumber;
	}

	public void setUserID(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static int getUserCount() {
		return UserCount;
	}

	public static void setUserCount(int userCount) {
		UserCount = userCount;
	}
}
