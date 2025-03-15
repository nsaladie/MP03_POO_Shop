package dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import model.*;

public class DaoImplMongoDB implements Dao {

	MongoCollection<Document> collection;
	ObjectId id;

	@Override
	public void connect() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		collection = mongoDatabase.getCollection("inventory");

	}

	public void connectToHistoricalInventory() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		collection = mongoDatabase.getCollection("historical_inventory");
	}

	public void connectToUsers() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		collection = mongoDatabase.getCollection("users");
	}

	public void connectToSales() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		collection = mongoDatabase.getCollection("sales");
	}

	@Override
	public Employee getEmployee(int user, String pw) {
		connectToUsers();
		try {
			Document doc = collection.find(eq("employeeId", user)).first();
			// Check if doc is not null and if doc is equals to the password send by user
			if (doc != null) {
				String password = doc.getString("password");
				
				if (password.equals(pw)) {
					Employee employee = new Employee();
					employee.setEmplyeeId(doc.getInteger("employeeId"));
					return employee;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> listProducts = new ArrayList<Product>();

		try {

			for (Document document : collection.find()) {
				Product product = new Product();

				product.setId(document.getInteger("id"));
				product.setName(document.getString("name"));

				Document wholesalerPriceDoc = (Document) document.get("wholesalerPrice");
				product.setWholesalerPrice(new Amount(wholesalerPriceDoc.getDouble("value")));
				product.setPublicPrice(new Amount(wholesalerPriceDoc.getDouble("value") * 2));

				product.setStock(document.getInteger("stock"));
				product.setAvailable(document.getBoolean("available"));

				listProducts.add(product);
			}
			Product.setTotalProducts(listProducts.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listProducts;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		boolean isExport = false;

		try {
			connectToHistoricalInventory();
			LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        
			ArrayList<Document> documents = new ArrayList<>();

			for (Product product : products) {
				Document wholesalerPriceDoc = new Document()
						.append("value", product.getWholesalerPrice().getValue())
						.append("currency", product.getWholesalerPrice().getCurrency());

				Document doc = new Document("_id", new ObjectId())
						.append("id", product.getId())
						.append("name", product.getName())
						.append("wholesalerPrice", wholesalerPriceDoc)
						.append("available", product.isAvailable())
						.append("stock", product.getStock())
						.append("create_at", now.format(formatter));

				documents.add(doc);
			}

			if (!documents.isEmpty()) {
				collection.insertMany(documents);
			}
			isExport = true;
		} catch (Exception e) {
			e.printStackTrace();
			isExport = false;
		}
		return isExport;
	}

	@Override
	public void addProduct(Product product) {
		try {

			Document wholesalerPriceDoc = new Document()
					.append("value", product.getWholesalerPrice().getValue())
					.append("currency", product.getWholesalerPrice().getCurrency());

			Document insertDocument = new Document("_id", new ObjectId())
					.append("name", product.getName())
					.append("wholesalerPrice", wholesalerPriceDoc)
					.append("available", product.isAvailable())
					.append("stock", product.getStock()).append("id", product.getId());

			collection.insertOne(insertDocument);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public void updateProduct(Product product) {
		try {
			collection.updateOne(eq("id", product.getId()), (set("stock", product.getStock())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(Product product) {
		try {
			collection.deleteOne(eq("id", product.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean writeSales(ArrayList<Sale> sales) {
		connectToSales();
		boolean isSaleExported = false;
		try {
			LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			ArrayList<Document> documents = new ArrayList<>();

			for (Sale product : sales) {
				Document saleDocument = new Document()
						.append("client", product.getClient().toString())
						.append("date",product.getDateSale());

				ArrayList<Document> productsList = new ArrayList<>();
				for (Product listProduct : product.getProducts()) {

					Document selectProduct = new Document()
							.append("name", listProduct.getName())
							.append("value", listProduct.getPublicPrice().getValue())
							.append("currency", listProduct.getPublicPrice().getCurrency());
					productsList.add(selectProduct);
				}

				saleDocument.append("products", productsList);
				saleDocument.append("amount", product.getAmount().getValue());
				saleDocument.append("create_at", now.format(formatter));
				documents.add(saleDocument);
			}

			if (!documents.isEmpty()) {
				collection.insertMany(documents);
			}
			isSaleExported = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaleExported;
	}

}
