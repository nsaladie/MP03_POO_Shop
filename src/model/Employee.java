package model;

import main.Logable;

public class Employee extends Person implements Logable {
	// Constants
	final private int EMPLOYEEDID = 123;
	final private String PASSWORD = "test";
	
	private int emplyeeId = EMPLOYEEDID;

	public int getEmplyeeId() {
		return emplyeeId;
	}

	public void setEmplyeeId(int emplyeeId) {
		this.emplyeeId = emplyeeId;
	}

	public int getEMPLOYEEDID() {
		return EMPLOYEEDID;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	// Check if the user and password send by user are equals to the constants
	public boolean login(int user, String password) {
		if (user == EMPLOYEEDID && password.equals(PASSWORD)) {
			return true;
		}
		return false;
	}

}
