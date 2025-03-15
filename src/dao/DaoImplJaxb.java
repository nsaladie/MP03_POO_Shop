package dao;

import java.util.ArrayList;

import dao.jaxb.JaxbMarshaller;
import dao.jaxb.JaxbUnMarshaller;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplJaxb implements Dao {

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

		JaxbUnMarshaller jaxbUnMarshaller = new JaxbUnMarshaller();
		return jaxbUnMarshaller.importInventory();
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		// TODO Auto-generated method stub
		JaxbMarshaller jaxbMarshall = new JaxbMarshaller();
		return jaxbMarshall.exportXml(product);
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
