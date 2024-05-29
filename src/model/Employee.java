package model;

import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;

public class Employee extends Person implements Logable {
	// Constants
	// final private int EMPLOYEEDID = 123;
	// final private String PASSWORD = "test";

	private int employeeId;
	private String password;

	private Dao dao = new DaoImplJDBC();

	public Employee(int id, String name, String pw) {
		super(name);
		// TODO Auto-generated constructor stub
		this.employeeId = id;
		this.password = pw;
	}

	public Employee() {

	}

	public int getEmplyeeId() {
		return employeeId;
	}

	public void setEmplyeeId(int emplyeeId) {
		this.employeeId = emplyeeId;
	}

	@Override
	public boolean login(int user, String password) {
		// TODO Auto-generated method stub
		try {
			dao.connect();
			Employee employee = dao.getEmployee(user, password);

			if (employee != null) {
				dao.disconnect();
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e);
		}

		return false;
	}

	// Check if the user and password send by user are equals to the constants
	// public boolean login(int user, String password) {
	// if (user == EMPLOYEEDID && password.equals(PASSWORD)) {
	// return true;
	// }
	// return false;
	// }
}
