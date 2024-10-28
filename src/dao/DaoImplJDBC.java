package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {

	private Connection connection;

	public DaoImplJDBC() {

	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/Shop";
		String user = "root";
		String password = "";

		try {
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Employee getEmployee(int user, String pw) {
		Employee employee = null;
		String select = "SELECT * FROM employee WHERE employeeId = ? AND password = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setInt(1, user);
			ps.setString(2, pw);
			// run query
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public void disconnect() {
		try {
			// TODO Auto-generated method stub
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		// TODO Auto-generated method stub
		return false;
	}

}
