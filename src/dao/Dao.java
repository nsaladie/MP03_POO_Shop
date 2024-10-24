package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;
import model.Product;

public interface Dao {
	public void connect() throws SQLException;

	public Employee getEmployee(int user, String pw);

	public void disconnect() throws SQLException;

	public ArrayList<Product> getInventory();

	public boolean writeInventory(ArrayList<Product> product);

}
