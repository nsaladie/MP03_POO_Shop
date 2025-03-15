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
	private JLabel lblPrice;
	private JLabel lblName;
	private JLabel lblStock;
	private JButton btnBack;
	private JButton btnOk;
	private int option;
	private Shop shop;
	private JLabel lblSymbol_1;
	private JLabel lblSymbol_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}

	public ProductView(Shop shop, int option) {
		this.shop = shop;
		this.option = option;
		initializeUI();
	}

	private void initializeUI() {
		setBounds(100, 100, 450, 500);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel contentPanel = createContentPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		setupLabelsAndFields();
	}

	private JPanel createContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(45, 45, 45));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		return contentPanel;
	}

	private void setupLabelsAndFields() {
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.WHITE);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panel.setBounds(35, 30, 360, 400);
		panel.setOpaque(false);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblProduct = new JLabel("PRODUCT");
		lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct.setFont(new Font("SansSerif", Font.BOLD, 28));
		lblProduct.setForeground(new Color(45, 45, 45));
		lblProduct.setBounds(0, 10, 360, 35);
		panel.add(lblProduct);

		lblName = createStyledLabel("Product Name", 30, 80);
		panel.add(lblName);

		nameProduct = createStyledTextField(180, 80);
		panel.add(nameProduct);

		lblStock = createStyledLabel("Product Stock", 30, 140);
		panel.add(lblStock);

		stockProduct = createStyledTextField(180, 140);
		panel.add(stockProduct);

		lblPrice = createStyledLabel("Product Price", 30, 200);
		panel.add(lblPrice);

		priceProduct = createStyledTextField(180, 200);
		panel.add(priceProduct);

		btnOk = createStyledButton("Ok", new Color(76, 175, 80), 50, 300);
		panel.add(btnOk);

		btnBack = createStyledButton("Back", new Color(244, 67, 54), 190, 300);
		panel.add(btnBack);

		JLabel lblSymbol = createRedAsterisk(160, 80);
		panel.add(lblSymbol);

		lblSymbol_1 = createRedAsterisk(160, 140);
		panel.add(lblSymbol_1);

		lblSymbol_2 = createRedAsterisk(160, 200);
		panel.add(lblSymbol_2);

		adjustViewByOption();
	}

	private JLabel createStyledLabel(String text, int x, int y) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		label.setForeground(new Color(80, 80, 80));
		label.setBounds(x, y, 130, 30);
		return label;
	}

	private JTextField createStyledTextField(int x, int y) {
		JTextField textField = new JTextField();
		textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		textField.setBounds(x, y, 150, 30);
		textField.setBackground(new Color(245, 245, 245));
		textField.setForeground(Color.DARK_GRAY);
		textField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		return textField;
	}

	private JButton createStyledButton(String text, Color color, int x, int y) {
		JButton button = new JButton(text);
		button.setFont(new Font("SansSerif", Font.BOLD, 16));
		button.setBounds(x, y, 100, 40);
		button.setBackground(color);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addActionListener(this);
		return button;
	}

	private JLabel createRedAsterisk(int x, int y) {
		JLabel label = new JLabel("*");
		label.setFont(new Font("SansSerif", Font.PLAIN, 16));
		label.setForeground(Color.RED);
		label.setBounds(x, y, 20, 20);
		return label;
	}

	private void adjustViewByOption() {
		switch (this.option) {
		case 2: // Add Product
			setTitle("Add Product");
			break;
		case 3: // Update Stock
			setTitle("Update Stock");
			priceProduct.setVisible(false);
			lblPrice.setVisible(false);
			lblSymbol_2.setVisible(false);
			break;
		case 9: // Delete Product
			setTitle("Delete Product");
			lblStock.setVisible(false);
			stockProduct.setVisible(false);
			lblSymbol_1.setVisible(false);
			lblPrice.setVisible(false);
			priceProduct.setVisible(false);
			lblSymbol_2.setVisible(false);
			break;
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
				name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
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
