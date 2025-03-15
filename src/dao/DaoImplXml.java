package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

import dao.xml.DomWriter;
import dao.xml.SaxReader;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplXml implements Dao {

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
		ArrayList<Product> products = null;
		File file = new File("./xml/inputInventory.xml");

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			SaxReader saxReader = new SaxReader();
			parser.parse(file, saxReader);
			products = saxReader.getProducts();

		} catch (ParserConfigurationException | SAXException e) {
			System.out.println("ERROR creating the parser");
		} catch (IOException e) {
			System.out.println("ERROR file not found");
		}
		return products;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		boolean getReport = false;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date = LocalDateTime.now();
		String nameFile = "inventory_" + date.format(formatter) + ".xml";

		DomWriter domWriter = new DomWriter(nameFile);
		getReport = domWriter.generateReport(product);

		return getReport;
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean writeSales(ArrayList<Sale> sales) {
		// TODO Auto-generated method stub
		return false;
	}

}
