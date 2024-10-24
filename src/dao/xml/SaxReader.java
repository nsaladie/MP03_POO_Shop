package dao.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {
	private ArrayList<Product> products;
	private int idProduct = 1;

	Product product;
	String value;
	String parsedElement;

	public ArrayList<Product> getProducts() {
		return products;
	}

	@Override
	public void startDocument() throws SAXException {
		this.products = new ArrayList<>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case "product":
			this.product = new Product(attributes.getValue("name"));
			this.product.setId(idProduct);
			break;
		case "wholesalerPrice":
			this.product.setMoney(attributes.getValue("currency"));
			break;
		}
		this.parsedElement = qName;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		value = new String(ch, start, length);
		switch (parsedElement) {
		case "product":
			break;
		case "wholesalerPrice":
			this.product.setWholesalerPrice(new Amount(Double.parseDouble(value)));
			// Set public price for the product
			this.product.setPublicPrice(new Amount(Double.parseDouble(value) * 2));
			break;
		case "stock":
			this.product.setStock(Integer.valueOf(value));
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("product")) {
			this.products.add(product);
			idProduct++;
		}
		this.parsedElement = "";
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
}
