package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Employee;
import model.Product;
import model.ProductHistory;

public class DaoImplHibernate implements Dao {

	private Session session;
	private Transaction tx;

	@Override
	public void connect() {
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		org.hibernate.SessionFactory sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
	}

	@Override
	public Employee getEmployee(int user, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() {
		session.close();
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> productsList = new ArrayList<Product>();
		connect();
		try {
			tx = session.beginTransaction();
			Query<Product> query = session.createQuery("FROM Product p", Product.class);
			List<Product> list = query.list();
			productsList.addAll(list);

			for (Product product : productsList) {
				product.setPrice(product.getPrice());
			}
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return productsList;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		Date today = new Date();
		boolean isExport = false;
		connect();
		try {
			tx = session.beginTransaction();
			for (Product product : products) {
				ProductHistory history = new ProductHistory();
				history.setIdProduct(product.getId());
				history.setName(product.getName());
				history.setPrice(product.getPrice());
				history.setStock(product.getStock());
				history.setCreatedAt(today);
				session.save(history);
			}
			tx.commit();
			isExport = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return isExport;
	}

	@Override
	public void addProduct(Product product) {
		connect();
		try {
			tx = session.beginTransaction();
			// Set 'price' from 'wholesalerPrice' to save data in the database
			product.setPrice(product.getWholesalerPrice().getValue());
			session.save(product);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	@Override
	public void updateProduct(Product product) {
		connect();
		try {
			tx = session.beginTransaction();
			session.update(product);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	@Override
	public void deleteProduct(Product product) {
		connect();
		try {
			tx = session.beginTransaction();
			session.remove(product);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
}
