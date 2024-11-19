package dao.xml;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Product;

public class DomWriter {
	private Document document;
	private String nameFile;
	private File file;

	public DomWriter(String name) {
		this.nameFile = name;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating document");
		}
	}

	public boolean generateReport(ArrayList<Product> products) {
		Element parentNode = document.createElement("products");
		parentNode.setAttribute("total", String.valueOf(products.size()));
		document.appendChild(parentNode);

		for (Product product : products) {
			Element newProduct = document.createElement("product");
			newProduct.setAttribute("id", String.valueOf(product.getId()));
			parentNode.appendChild(newProduct);

			Element productName = document.createElement("name");
			productName.setTextContent(String.valueOf(product.getName()));
			newProduct.appendChild(productName);

			Element productPrice = document.createElement("price");
			productPrice.setAttribute("currency", product.getWholesalerPrice().getCurrency());
			productPrice.setTextContent(String.valueOf(product.getWholesalerPrice().getValue()));
			newProduct.appendChild(productPrice);

			Element productStock = document.createElement("stock");
			productStock.setTextContent(String.valueOf(product.getStock()));
			newProduct.appendChild(productStock);
		}
		return generateXml();

	}

	private boolean generateXml() {
		boolean isFileGenerate = false;
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();

			Source source = new DOMSource(document);
			this.file = new File("./xml/" + this.nameFile);

			FileWriter fw = new FileWriter(this.file);
			PrintWriter pw = new PrintWriter(fw);
			Result result = new StreamResult(pw);

			transformer.transform(source, result);

			isFileGenerate = true;
		} catch (IOException e) {
			System.out.println("Error when creating writter file");
		} catch (TransformerException e) {
			System.out.println("Error transforming the document");
		}
		return isFileGenerate;
	}
}
