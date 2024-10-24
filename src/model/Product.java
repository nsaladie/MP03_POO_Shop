package model;

public class Product {
	private int id;
	private String name;
	private Amount publicPrice;
	private Amount wholesalerPrice;
	private String money;
	private boolean available;
	private int stock;
	private static int totalProducts;

	final static double EXPIRATION_RATE = 0.60;

	public Product(String name, double wholesalerPrice, boolean available, int stock) {
		super();
		this.id = totalProducts + 1;
		this.name = name;
		this.wholesalerPrice = new Amount(wholesalerPrice); // Initialisation with the Amount Builder
		this.publicPrice = new Amount(wholesalerPrice * 2);
		this.available = available;
		this.stock = stock;
		totalProducts++;
	}

	// Constructor for read XML
	public Product(String name) {
		this.name = name;
	}

	public void setMoney(String money) {
		this.money = (money);
	}

	public String getMoney() {
		return this.money;
	}

	@Override
	public String toString() {
		return "Product [Nombre = " + name + "," + publicPrice + ", Stock = " + stock + "] ";
	}

	public String listProduct() {
		return name + "," + publicPrice.amountSale() + ";";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount publicPrice) {
		this.publicPrice = publicPrice;
	}

	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public static int getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}

	public void expire() {
		double newValue = this.publicPrice.getValue() * (1 - EXPIRATION_RATE);
		this.publicPrice.setValue(newValue);
	}

}
