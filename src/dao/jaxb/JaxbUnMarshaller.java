package dao.jaxb;

import java.io.File;
import javax.xml.bind.*;
import java.util.ArrayList;

import model.*;

public class JaxbUnMarshaller {

	public ArrayList<Product> importInventory() {
		ArrayList<Product> listProduct = new ArrayList<Product>();
		// read from xml to java object
		ProductList products = null;
		try {
			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			products = (ProductList) unmarshaller.unmarshal(new File("./jaxb/inputInventory.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		for (Product p : products.getListProducts()) {
			if (p.getWholesalerPrice() != null) {
				p.setPublicPrice(new Amount(p.getWholesalerPrice().getValue() * 2));
			}
			if (p.getStock() >= 1) {
				p.setAvailable(true);
			}
			System.out.println(p.getId() + p.toString());
		}
		listProduct.addAll(products.getListProducts());

		return listProduct;
	}
}
