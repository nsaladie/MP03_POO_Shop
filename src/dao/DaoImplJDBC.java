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
		String select = "SELECT * FROM employee WHERE employeeId = ? AND password = ?;";
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
		ArrayList<Product> listProduct = new ArrayList<Product>();

		String select = "SELECT * FROM inventory;";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(select);
			while (rs.next()) {
				listProduct.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("wholesalerPrice"),
						rs.getBoolean("available"), rs.getInt("stock")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		boolean isExported = false;

		String exportInventory = "INSERT INTO historical_inventory (id_product, name, wholesalerPrice, available, stock) VALUES (?,?,?,?,?);";

		for (Product dataProduct : product) {
			// Get the data of all product in the list
			int id = dataProduct.getId();
			String name = dataProduct.getName();
			double wholesalerPrice = dataProduct.getWholesalerPrice().getValue();
			boolean isAvailable = dataProduct.isAvailable();
			int stock = dataProduct.getStock();

			try {
				PreparedStatement addPs = this.connection.prepareStatement(exportInventory);
				addPs.setInt(1, id);
				addPs.setString(2, name);
				addPs.setDouble(3, wholesalerPrice);
				addPs.setBoolean(4, isAvailable);
				addPs.setInt(5, stock);

				int rowsAffected = addPs.executeUpdate();
				if (rowsAffected > 0) {
					isExported = true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return isExported;
	}

	@Override
	public void addProduct(Product product) {
		String addProduct = "INSERT INTO Inventory (name, wholesalerPrice, available, stock) VALUES (?,?,?,?);";

		try {
			PreparedStatement addPs = this.connection.prepareStatement(addProduct);
			addPs.setString(1, product.getName());
			addPs.setDouble(2, product.getWholesalerPrice().getValue());
			addPs.setBoolean(3, product.isAvailable());
			addPs.setInt(4, product.getStock());

			addPs.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(Product product) {
		String updateProduct = "UPDATE Inventory SET stock = ? WHERE id = ?;";

		try {
			PreparedStatement updatePs = this.connection.prepareStatement(updateProduct);
			updatePs.setInt(1, product.getStock());
			updatePs.setInt(2, product.getId());

			updatePs.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(Product product) {
		String deleteProduct = "DELETE FROM Inventory WHERE name = ?;";

		try {
			PreparedStatement deletePs = this.connection.prepareStatement(deleteProduct);
			deletePs.setString(1, product.getName());

			deletePs.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
