package view;

import main.Shop;
import model.Product;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class InventoryView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private ArrayList<Product> inventory;
	private DefaultTableModel model;
	private JButton backButton;
	private JTable table;
	private JPanel panel;
	private Shop shop;

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
	 */
	public InventoryView(Shop shop) {
		this.shop = shop;
		setTitle("Inventory Shop");

		setBounds(100, 100, 540, 460);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 29, 506, 330);
		panel.setLayout(null);
		contentPanel.add(panel);

		// Define column names for the table
		String[] columnNames = { "Id Product", "Product Name", "Product Price", "WholeSale Price", "Stock" };
		// Create a new DefaultTableModel with specified column names and 0 rows
		model = new DefaultTableModel(columnNames, 0);
		// Create a JTable with a DefaultTableModel containing an empty data array and
		// the same column names
		table = new JTable(new DefaultTableModel(new Object[][] {},
				new String[] { "Id Product", "Product Name", "Product Price", "WholeSale Price", "Stock" }));
		table.setEnabled(false);

		table.setFont(new Font("SansSerif", Font.PLAIN, 15));
		// Set auto resize mode for the table columns
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// Add Scroll to the Table
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 50, 506, 280);
		panel.add(scrollPane);

		// Call the method loadInventory()
		loadInventory();

		JLabel lblTitle = new JLabel("Inventory - Shop");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblTitle.setBounds(0, 10, 176, 30);
		panel.add(lblTitle);

		backButton = new JButton("Back");
		backButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		backButton.setBounds(212, 380, 91, 31);
		backButton.addActionListener(this);
		contentPanel.add(backButton);

	}

	private void loadInventory() {
		// Get the information of shop.getInventory() and saved in an ArrayList
		inventory = shop.getInventory();
		// for each that runs through all the ArrayList of inventory
		for (Product product : inventory) {
			// Add a new row with this information
			model.addRow(new Object[] { 
					product.getId(), // Product ID
					capitalizeFirstLetter(product.getName()), // Product name
					product.getPublicPrice().amountSale(), // Product public price
					product.getWholesalerPrice().amountSale(), // Product wholesale price
					product.getStock() // Product stock
			});
			// Update the table model after adding each row
			table.setModel(model);
		}
	}

	/*
	 *  Put the first character in upper 
	 */
	private String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == backButton) {
			dispose();
		}

	}
}
