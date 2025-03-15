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

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
		});
	}

	public InventoryView(Shop shop) {
		this.shop = shop;
		setTitle("Inventory - Shop");

		setBounds(100, 100, 600, 500);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(45, 45, 45));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		setupMainPanel();
		setupTable();
		setupBackButton();

		loadInventory();
	}

	private void setupMainPanel() {
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.WHITE);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			}
		};
		panel.setBounds(20, 20, 540, 370);
		panel.setOpaque(false);
		panel.setLayout(null);
		contentPanel.add(panel);

		JLabel lblTitle = new JLabel("Inventory");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblTitle.setForeground(new Color(50, 50, 50));
		lblTitle.setBounds(0, 10, 540, 30);
		panel.add(lblTitle);
	}

	private void setupTable() {
		String[] columnNames = { "Id", "Product Name", "Price", "Wholesale Price", "Stock" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowSelectionAllowed(false);
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setRowHeight(30);
		table.setBackground(new Color(245, 245, 245));
		table.setForeground(Color.DARK_GRAY);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		header.setFont(new Font("SansSerif", Font.BOLD, 14));
		header.setBackground(new Color(100, 149, 237));
		header.setForeground(Color.WHITE);
		header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(50); // ID
		columnModel.getColumn(1).setPreferredWidth(150); // Product Name
		columnModel.getColumn(2).setPreferredWidth(100); // Price
		columnModel.getColumn(3).setPreferredWidth(120); // Wholesale Price
		columnModel.getColumn(4).setPreferredWidth(80); // Stock

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 50, 520, 300);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		panel.add(scrollPane);
	}

	private void setupBackButton() {
		backButton = new JButton("Back");
		backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		backButton.setBounds(250, 410, 100, 40);
		backButton.setBackground(new Color(244, 67, 54));
		backButton.setForeground(Color.WHITE);
		backButton.setFocusPainted(false);
		backButton.setBorder(BorderFactory.createEmptyBorder());
		backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backButton.addActionListener(this);
		contentPanel.add(backButton);
	}

	private void loadInventory() {
		// Get the information of shop.getInventory() and saved in an ArrayList
		inventory = shop.getInventory();
		// for each that runs through all the ArrayList of inventory
		for (Product product : inventory) {
			// Add a new row with this information
			model.addRow(new Object[] { product.getId(), // Product ID
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
	 * Put the first character in upper
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
