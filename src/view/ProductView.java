package view;

import main.*;
import model.Product;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class ProductView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel panel;
	private JTextField nameProduct;
	private JTextField stockProduct;
	private JTextField priceProduct;
	private JButton btnBack;
	private JButton btnOk;
	private int option;
	private Shop shop;
	private JLabel lblSymbol_1;
	private JLabel lblSymbol_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
			}
		});
	}

	/**
	 * Create the dialog.
	 * 
	 * @param option
	 * @param shop
	 */
	public ProductView(Shop shop, int option) {
		this.shop = shop;
		this.option = option;
		initializeUI();
	}

	private void initializeUI() {
		setBounds(100, 100, 425, 450);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel contentPanel = createContentPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		setupLabelsAndFields();
	}

	private JPanel createContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		return contentPanel;
	}

	private void setupLabelsAndFields() {
		panel = new JPanel();
		panel.setBounds(39, 37, 331, 349);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblProduct = new JLabel("PRODUCT");
		lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblProduct.setBounds(0, 10, 331, 31);
		panel.add(lblProduct);

		JLabel lblNameProduct = new JLabel("Product Name");
		lblNameProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblNameProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNameProduct.setBounds(21, 100, 130, 30);
		panel.add(lblNameProduct);

		nameProduct = new JTextField();
		nameProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		nameProduct.setBounds(165, 100, 144, 30);
		panel.add(nameProduct);
		nameProduct.setColumns(10);

		JLabel lblStockProduct = new JLabel("Product Stock");
		lblStockProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblStockProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblStockProduct.setBounds(21, 155, 130, 30);
		panel.add(lblStockProduct);

		stockProduct = new JTextField();
		stockProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		stockProduct.setColumns(10);
		stockProduct.setBounds(165, 155, 144, 30);
		panel.add(stockProduct);

		JLabel lblPriceProduct = new JLabel("Product Price");
		lblPriceProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblPriceProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblPriceProduct.setBounds(21, 208, 130, 30);
		panel.add(lblPriceProduct);

		priceProduct = new JTextField();
		priceProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		priceProduct.setColumns(10);
		priceProduct.setBounds(165, 208, 144, 30);
		panel.add(priceProduct);

		btnOk = new JButton("Ok");
		btnOk.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnOk.setBounds(40, 286, 104, 31);
		btnOk.addActionListener(this);
		panel.add(btnOk);

		btnBack = new JButton("Back");
		btnBack.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnBack.setBounds(187, 286, 104, 31);
		btnBack.addActionListener(this);
		panel.add(btnBack);

		JLabel lblSymbol = new JLabel("*");
		lblSymbol.setForeground(Color.RED);
		lblSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblSymbol.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblSymbol.setBounds(143, 105, 22, 21);
		panel.add(lblSymbol);

		lblSymbol_1 = new JLabel("*");
		lblSymbol_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSymbol_1.setForeground(Color.RED);
		lblSymbol_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblSymbol_1.setBounds(143, 160, 22, 21);
		panel.add(lblSymbol_1);

		lblSymbol_2 = new JLabel("*");
		lblSymbol_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSymbol_2.setForeground(Color.RED);
		lblSymbol_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblSymbol_2.setBounds(143, 213, 22, 21);
		panel.add(lblSymbol_2);

		// Depending the option that was pass in ShopView hides the labels and inputs
		// and put a correct title
		switch (this.option) {
		case 2:
			// Put a title to the dialog
			setTitle("Add Product");
			break;
		case 3:
			// Put a title to the dialog
			setTitle("Update Stock");
			// Hides the label and input of price
			lblPriceProduct.setVisible(false);
			priceProduct.setVisible(false);
			lblSymbol_2.setVisible(false);
			break;
		case 9:
			// Put a title to the dialog
			setTitle("Delete Product");
			// Hides the label and inputs of stock and price
			lblStockProduct.setVisible(false);
			stockProduct.setVisible(false);
			lblPriceProduct.setVisible(false);
			priceProduct.setVisible(false);
			lblSymbol_1.setVisible(false);
			lblSymbol_2.setVisible(false);

		}
	}

	public void addProduct() {
		// Receives the parameters of the ProdcutView, trim() removes blanks at both
		// ends of the string
		String name = nameProduct.getText().trim();
		String stock = stockProduct.getText().trim();
		String price = priceProduct.getText().trim();

		if (name.isEmpty() || stock.isEmpty() || price.isEmpty()) {
			JOptionPane.showMessageDialog(this, "The parameters Name/Stock/Price can't be empty", "Invalid Type",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			// Pass the parameter to the correct type of data
			double wholesalerPrice = Double.parseDouble(price);
			int productStock = Integer.parseInt(stock);

			Product exist = shop.findProduct(name);
			// Check if the product send by the user not exist
			if (exist == null) {
				// Add the new product to the shop
				shop.addProduct(new Product(name, wholesalerPrice, true, productStock));
				JOptionPane.showMessageDialog(this, "New product successfully created", "Create Product",
						JOptionPane.INFORMATION_MESSAGE);
				// Return to the ShopView
				dispose();
			} else {
				// Report to the user that the product sent it's already created
				JOptionPane.showMessageDialog(this, "The product " + name + " has already been created",
						"Product Alredy Created", JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException number) {
			// Report to the user that sent incorrect type to stock and price
			JOptionPane.showMessageDialog(this, "Please enter valid numeric values for stock and price",
					"Incorrect Type", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void updateStock() {
		// Receives the parameters of the ProdcutView, trim() removes blanks at both
		// ends of the string
		String name = nameProduct.getText().trim();
		String stock = stockProduct.getText().trim();

		if (name.isEmpty() || stock.isEmpty()) {
			JOptionPane.showMessageDialog(this, "The parameters Name/Stock can't be empty", "Invalid Type",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			// Pass the parameter to the correct type of data
			int productStock = Integer.parseInt(stock);

			Product product = shop.findProduct(name);
			// Check if the product send by the user exist
			if (product != null) {
				int totalStock = product.getStock() + productStock;
				// Update the stock
				shop.updateStockProduct(totalStock, product);
				JOptionPane.showMessageDialog(this, "Product stock updated successfully", "Update Stock",
						JOptionPane.INFORMATION_MESSAGE);
				// Return to the ShopView
				dispose();
			} else {
				// Report to the user that the product is not created, and can't update stock
				JOptionPane.showMessageDialog(this, "Product not found, unable to update stock", "ERROR: Update Stock",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException number) {
			JOptionPane.showMessageDialog(this, "Invalid input for stock, only numeric values are accepted",
					"Incorret Type", JOptionPane.WARNING_MESSAGE);

		}

	}

	public void deleteProduct() {
		// Receives the parameters of the ProdcutView, trim() removes blanks at both
		// ends of the string
		String name = nameProduct.getText().trim();

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "The parameters Name can't be empty", "Invalid Type",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Product exist = shop.findProduct(name);
		// Check if the product send by the user exist
		if (exist != null) {
			// Remove the product
			shop.removeProduct(exist);
			JOptionPane.showMessageDialog(this, "Product " + name + " successfully deleted", "Delete Product",
					JOptionPane.INFORMATION_MESSAGE);
			// Return to the ShopView
			dispose();
		} else {
			// Report that the product can't be deleted because is not created
			JOptionPane.showMessageDialog(this, "Product not found, unable to delete product",
					"ERROR: The product can't be deleted", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			// Depending of the option sent by the ShopView call the correct method
			switch (this.option) {
			case 2:
				addProduct();
				break;
			case 3:
				updateStock();
				break;
			case 9:
				deleteProduct();
				break;
			}
		}

		if (e.getSource() == btnBack) {
			// Return to the ShopView
			dispose();
		}
	}
}
