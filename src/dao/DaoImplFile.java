package dao;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.*;

public class DaoImplFile implements Dao {

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getEmployee(int user, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		ArrayList<Product> listProduct = new ArrayList<Product>();
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
				listProduct.add(new Product(product, wholeSale, true, stock));
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
		return listProduct;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		boolean response;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		try {
			LocalDateTime date = LocalDateTime.now();
			String nameFile = "inventory_" + date.format(formatter) + ".txt";

			String directory = "./files/";
			File file = new File(directory);

			if (!file.exists()) {
				directory = JOptionPane.showInputDialog("Name of the new directory:");
				file = new File("./" + directory);
				file.mkdir();
			}

			file = new File(file, nameFile);
			file.createNewFile();
			// File writer to write into the file
			FileWriter write = new FileWriter(file);
			// PrintWriter to write formatted text to a file
			PrintWriter writeFile = new PrintWriter(write);

			int count = 0;
			// Iterate over sales to export data
			for (Product listProducts : product) {
				// Generate ID for each product
				String id = String.valueOf(listProducts.getId());
				String division = ";";
				String products = "Product:" + listProducts.getName();
				String stock = "Stock:" + listProducts.getStock();

				String exportData = id + division + products + division + stock + division + "\n";

				// Write the export data to the file
				writeFile.append(exportData);
				count++;
			}
			// Write the last line of the document
			writeFile.append("Total number of the products:" + count + ";");
			// Close the PrintWriter and FileWriter
			writeFile.close();
			write.close();

			response = true;

		} catch (IOException e) {
			response = false;
		}
		return response;
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

}
