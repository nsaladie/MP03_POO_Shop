package main;

import model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
	private Amount cash;
	private ArrayList<Product> inventory;
	private ArrayList<Sale> sales;

	final static double TAX_RATE = 1.04;

	public Shop() {
		this.cash = new Amount(100.00);
		this.inventory = new ArrayList<>();
		this.sales = new ArrayList<>();
	}

	public static void main(String[] args) {
		Shop shop = new Shop();
		// Load initial inventory
		shop.loadInventory();
		// Login for the employer
		shop.initSession();

		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		boolean exit = false;

		do {
			System.out.println("\n");
			System.out.println("===========================");
			System.out.println("Menu principal miTienda.com");
			System.out.println("===========================");
			System.out.println("1) Contar caja");
			System.out.println("2) Añadir producto");
			System.out.println("3) Añadir stock");
			System.out.println("4) Marcar producto proxima caducidad");
			System.out.println("5) Ver inventario");
			System.out.println("6) Venta");
			System.out.println("7) Ver ventas");
			System.out.println("8) Ver ventas total");
			System.out.println("9) Eliminar producto del inventario");
			System.out.println("10) Salir programa");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();
			System.out.println();

			switch (opcion) {
			case 1:
				shop.showCash(shop);
				break;

			case 2:
				shop.addProduct();
				break;

			case 3:
				shop.addStock();
				break;

			case 4:
				shop.setExpired();
				break;

			case 5:
				shop.showInventory();
				break;

			case 6:
				shop.sale();
				break;

			case 7:
				shop.showSales();
				break;

			case 8:
				shop.showChashSale();
				break;

			case 9:
				shop.eliminateProduct();
				break;

			case 10:
				exit = true;
				break;

			default:
				System.out.println("Opción incorrecta");
				break;
			}
		} while (!exit);
	}

	/*
	 * Login the session of the employee
	 */
	public void initSession() {
		Scanner scanner = new Scanner(System.in);
		Employee worker = new Employee();
		boolean login = false;

		do {
			System.out.print("Identificacion de usuario: ");
			while (!scanner.hasNextInt()) {
				System.err.println("Has de introducir solo caracteres numericos");
				System.out.print("Identificacion de usuario: ");
				scanner.next();
			}
			int user = scanner.nextInt();
			System.out.print("Contraseña del usuario: ");
			String password = scanner.next();
			// Save the result of the method in the variable login
			login = worker.login(user, password);
			// If login is false, report to the user that identification employee or
			// password are incorrect
			if (!login) {
				System.err.println("Los datos de usuario o contraseña son incorrectos.");
				System.err.println("Por favor, reintroduzca los datos correctos.");
			}
		} while (!login);

		if (login) {
			System.out.println("Login correcto.");
		}
	}

	/**
	 * load initial inventory to shop
	 */
	public void loadInventory() {
		try {
			File inventoryFile = new File("./files/inputInventory.txt");
			// If the file not exists informs to the user.
			if (!inventoryFile.exists()) {
				System.out.println("El archivo de carga de inventario no existe");
			}
			// Read the file
			FileReader fileReader = new FileReader(inventoryFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = reader.readLine();
			// Read each line of the file
			while (line != null) {
				// Separates the line when found a ';' and saves it in an array
				String[] parts = line.split(";");
				// Create the variables that will save the data
				String product = "";
				double wholeSale = 0.0;
				int stock = 0;

				for (int i = 0; i < parts.length; i++) {
					// Separates the variable parts when found a ':' and saves it in an array
					String[] key = parts[i].split(":");

					switch (i) {
					// Case 0: product name
					case 0:
						// Save only the product name
						product = key[1];
						break;
					// Case 1: wholeSale
					case 1:
						// Convert the string to a double and save as wholesale price
						wholeSale = Double.parseDouble(key[1]);
						break;
					// Case 2: stock
					case 2:
						// Convert the string to an integer and save as stock
						stock = Integer.parseInt(key[1]);
						break;
					}

				}
				// Add a new product
				addProduct(new Product(product, wholeSale, true, stock));
				// Read the next line
				line = reader.readLine();
			}
			// Close the bufferedReader and the fileReader
			reader.close();
			fileReader.close();

		} catch (IOException e) {
			// Inform the user if there was a problem with the file
			System.out.println("Ha habido algún problema con el fichero");
		}
	}

	/**
	 * show current total cash
	 */
	private void showCash(Shop shop) {
		System.out.println("Dinero actual: " + shop.cash);

	}

	/*
	 * Shows the total price of all sales
	 */
	private void showChashSale() {
		double amount = 0;
		int count = 0;
		for (Sale sale : sales) {
			if (sale != null) {
				amount += sale.getAmount().getValue();
				count++;
			}
		}
		// If the program don't have any sales inform to the user
		if (count == 0) {
			System.out.println("No se ha realizado ninguna venta.");
		} else {
			System.out.println("Dinero total de las ventas: " + amount + "€");
		}
	}

	/**
	 * add a new product to inventory getting data from console
	 */
	public void addProduct() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		System.out.print("Precio mayorista: ");
		double wholesalerPrice = scanner.nextDouble();
		System.out.print("Stock: ");
		int stock = scanner.nextInt();

		Product exist = findProduct(name);
		// Search if the product is already in the inventory
		if (exist == null) {
			addProduct(new Product(name, wholesalerPrice, true, stock));
		} else {
			System.out.println("El Producto que estas intentado añadir ya existe");
		}
	}

	/**
	 * add stock for a specific product
	 */
	public void addStock() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();
		Product product = findProduct(name);

		if (product != null) {
			// ask for stock
			System.out.print("Seleccione la cantidad a añadir: ");
			int stock = scanner.nextInt();
			// update stock product
			int stockTotal = product.getStock() + stock;
			product.setStock(stockTotal);
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

		} else {
			System.out.println("No se ha encontrado el producto con nombre " + name);
		}
	}

	/**
	 * set a product as expired
	 */
	private void setExpired() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();

		Product product = findProduct(name);

		if (product != null) {
			// Call to the function expire that it is in the class Product
			product.expire();
			System.out.println("La fecha de caducidad del producto: " + name
					+ " esta proximamente. El precio del producto se ha actualizado a:" + product.getPublicPrice());

		}
	}

	/**
	 * show all inventory
	 */
	public void showInventory() {
		System.out.println("Contenido actual de la tienda:");
		System.out.println();
		for (Product product : inventory) {
			if (product != null) {
				System.out.println(product.toString());
			}
		}
	}

	/*
	 * Get if the client is premium
	 */
	public boolean isPremium() {
		Scanner sc = new Scanner(System.in);
		System.out.println("¿El cliente es premium? (Si/No)");
		String answer = sc.next().toLowerCase();
		boolean validInput = false;

		// Loop that tests the if-response with si or no, if not the program ask again
		// the answer
		do {
			if (answer.equals("si") || answer.equals("no")) {
				validInput = true;
			} else {
				System.out.println("Por favor, responde con 'Si' o 'No'.");
				answer = sc.next().toLowerCase();
			}
		} while (!validInput);

		if (answer.equals("si")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * make a sale of products to a client
	 */
	public void sale() {
		// Save in the variable if the user is premium or not
		boolean premiumClient = isPremium();

		Scanner sc = new Scanner(System.in);
		// ask for client name
		System.out.println("Realizar venta, escribir nombre cliente");
		String client = sc.nextLine();
		int count = 0;
		// Create a array of 10 positions of Product, in this array will keep the name
		// of the products that the user want to buy
		ArrayList<Product> saleProducts = new ArrayList<>();
		// sale product until input name is not 0
		double totalAmount = 0.0;

		String name = "";
		// Call the class Client
		Client clientSale = new Client(client);

		while (!name.equals("0")) { // Check index and limit the loop to 10 products
			// Shows the list of products that are in stock.
			for (Product product : inventory) {
				if (product != null && product.getStock() > 0) {
					System.out.println(product.toString());
				}
			}

			System.out.println("\nIntroduce el nombre del producto, escribir 0 para terminar:");
			name = sc.nextLine();

			if (name.equals("0")) {
				break;
			}
			// Search is the product exist
			Product product = findProduct(name);
			boolean productAvailable = false;

			if (product != null && product.isAvailable()) {
				productAvailable = true;
				totalAmount += product.getPublicPrice().getValue();
				product.setStock(product.getStock() - 1);
				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}
				saleProducts.add(product);
				System.out.println("\nProducto añadido con éxito");
				// Variable that validates if at least one product has been purchased
				count++;
			}

			if (!productAvailable) {
				System.out.println("\nProducto no encontrado o sin stock");
			}
		}
		// If they have purchased at least one product
		if (count > 0) {
			// show cost total
			totalAmount = totalAmount * TAX_RATE;
			double totalCash = cash.getValue();
			cash.setValue(totalAmount + totalCash);
			// Create a class type Date
			LocalDateTime date = LocalDateTime.now();
			// Save in the sale, the name of clients, products and total price.
			sales.add(new Sale(client.trim(), saleProducts, totalAmount, date));

			Amount totalSale = new Amount(totalAmount);
			System.out.println("\nVenta realizada con éxito, total: " + totalSale.amountSale());

			// If the client is premium
			if (premiumClient) {
				// Create a newObject premium of the class PremiumClient
				PremiumClient premium = new PremiumClient(client);
				// Call the method addPoints, we pass the param totalSale.
				premium.addPoints(totalSale);
				System.out.println(premium);
			}
			// If the total of the amount of the sale exceeds 50
			if (!clientSale.pay(totalSale)) {
				double difference = clientSale.getAmount().getValue() - totalAmount;
				Amount owe = new Amount(difference);
				System.err.println("Cliente debe: " + owe.amountSale());
			}
		} else {
			System.out.println("El cliente " + client + " no ha comprado ningún producto");
		}
	}

	/**
	 * show all sales
	 */
	private void showSales() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Lista de ventas:");
		String date = "";
		int count = 0;

		for (Sale sale : sales) {
			int total = 0;
			if (sale != null) {
				System.out.println(sale);
				// Save the date with the correct format in the variable
				date = sale.getYearSale();
				count++;
			}
		}
		if (count > 0) {
			System.out.print("\nDeseas escribir todas las ventas en un fichero (Si/No)? ");
			String answer = sc.next();

			if (answer.equalsIgnoreCase("Si")) {
				createFileSale(date);
			} else {
				System.out.println("No ha querido exportar las ventas a un fichero");
			}

		} else {
			System.out.println("No hay ninguna venta a fecha actual");
		}
	}

	/*
	 * Create a file that save all the Sale
	 */
	private void createFileSale(String date) {
		try {
			String nameFile = "sales_" + date + ".txt";
			File file = new File("./files/" + nameFile);
			// Create a new file
			file.createNewFile();
			// File writer to write into the file
			FileWriter write = new FileWriter(file);
			// PrintWriter to write formatted text to a file
			PrintWriter writeFile = new PrintWriter(write);
			int position = 1;

			// Iterate over sales to export data
			for (Sale sale : sales) {
				// Generate ID for each sale
				String id = String.valueOf(position);
				String division = ";";
				// Data of the client of the sale
				String client = "Client=" + sale.getClient();
				String dateSale = "Date=" + sale.getDateSale();

				// List of products in the sale
				ArrayList<Product> productsList = sale.getProducts();
				String products = "Products=";
				for (Product product : productsList) {
					products += product.listProduct();
				}
				// Total amount of the sale
				Amount amount = sale.getAmount();
				String totalAmount = "Amount=" + amount.priceExport();

				String exportData = id + division + client + division + dateSale + division + "\n" + id + division
						+ products + "\n" + id + division + totalAmount + division + "\n";

				// Write the export data to the file
				writeFile.append(exportData);
				// Increment the position for the next sale
				position++;
			}
			// Close the PrintWriter and FileWriter
			writeFile.close();
			write.close();

			System.out.println("Datos exportados al fichero: " + file);

		} catch (IOException e) {
			System.out.println("Hay algun problema con el fichero");
		}
	}

	/*
	 * Delete product
	 */
	public void eliminateProduct() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Nombre del producto que quieres eliminar: ");
		String product = sc.next();

		Product exist = findProduct(product);

		if (exist != null) {
			inventory.remove(exist);
			System.out.println("Producto " + product + " eliminado correctamente");
		} else {
			System.out.println("El producto " + product + " no se encuentra en el inventario");
		}
	}

	/**
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		inventory.add(product);
	}

	/**
	 * find product by name
	 * 
	 * @param product name
	 */
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
				return inventory.get(i);
			}
		}
		return null;

	}

}