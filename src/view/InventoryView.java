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

		String[] columnNames = { "Id Product", "Product Name", "Product Price", "WholeSale Price", "Stock" };
		model = new DefaultTableModel(columnNames, 0);
		table = new JTable(new DefaultTableModel(new Object[][] {},
				new String[] { "Id Product", "Product Name", "Product Price", "WholeSale Price", "Stock" }));
		table.setFont(new Font("SansSerif", Font.PLAIN, 15));

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// Add Scroll to the Table
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 50, 506, 280);
		panel.add(scrollPane);

		loadInventory();

		JLabel lblNewLabel = new JLabel("Inventory - Shop");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblNewLabel.setBounds(0, 10, 176, 30);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnNewButton.setBounds(212, 380, 91, 31);
		contentPanel.add(btnNewButton);

	}

	private void loadInventory() {
		inventory = shop.getInventory();

		for (Product product : inventory) {
			model.addRow(new Object[] { product.getStock(), product.getPublicPrice().amountSale(),
					product.getWholesalerPrice().amountSale(), product.getName(), product.getId() });
			table.setModel(model);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == backButton) {
			dispose();
		}

	}
}
