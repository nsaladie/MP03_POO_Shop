package dao.jaxb;

import java.io.File;
import javax.xml.bind.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Product;
import model.ProductList;

public class JaxbMarshaller {
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	File file = new File("./jaxb/");
	String fileName = "inventory_";

	public JaxbMarshaller() {

	}

	public boolean exportXml(ArrayList<Product> product) {
		boolean isExport = false;
		try {
			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Marshaller marshaller = context.createMarshaller();
			ProductList products = new ProductList(product);

			LocalDateTime date = LocalDateTime.now();
			this.fileName += date.format(this.formatter) + ".xml";

			marshaller.marshal(products, new File(this.file, this.fileName));
			isExport = true;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return isExport;
	}
}
