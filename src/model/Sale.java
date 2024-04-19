package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Sale {
	private Client client;
	private ArrayList<Product> products;
	private Amount amount;
	private LocalDateTime dateSale;

	public Sale(String client, ArrayList<Product> products, double amount, LocalDateTime date) {
		super();
		this.client = new Client(client);
		this.products = products;
		this.amount = new Amount(amount);
		this.dateSale = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getDateSale() {
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		// Save the date with the format correct in a variable String
		String dateFormat = dateSale.format(formatDate);
		return dateFormat;
	}

	public String getYearSale() {
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Save the date with the format correct in a variable String
		String dateFormat = dateSale.format(formatDate);
		return dateFormat;
	}

	@Override
	public String toString() {
		return "Ventas [Cliente " + client + ", Productos : " + products.toString() + "," + amount + ", Fecha Venta : "
				+ this.getDateSale() + "]";
	}
}
