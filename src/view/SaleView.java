package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.Shop;
import model.Product;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SaleView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Shop shop;
	private JPanel contentPanel;
	private JPanel innerPanel;
	private JTextField clientName;
	private JList<Product> productList;
	private DefaultListModel<Product> productListModel;
	private JButton btnOk;
	private JButton btnBack;

	public SaleView(Shop shop) {
		this.shop = shop;
		initializeUI();
		loadProducts();
	}

	private void initializeUI() {
		setBounds(100, 100, 430, 450);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Make Sale");

		// Principal Panel
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		// Panel inner
		innerPanel = new JPanel();
		innerPanel.setBounds(20, 20, 380, 380);
		innerPanel.setLayout(null);
		innerPanel.setBackground(Color.WHITE);
		innerPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
		contentPanel.add(innerPanel);

		JLabel lblTitle = createLabel("Sale", new Font("SansSerif", Font.BOLD, 26), SwingConstants.CENTER, 140, 15, 100,
				35);
		lblTitle.setForeground(new Color(50, 50, 50));
		innerPanel.add(lblTitle);

		JLabel lblClientName = createLabel("Client Name:", new Font("SansSerif", Font.PLAIN, 16), SwingConstants.LEFT,
				25, 60, 100, 25);
		lblClientName.setForeground(Color.DARK_GRAY);
		innerPanel.add(lblClientName);

		clientName = new JTextField();
		clientName.setFont(new Font("SansSerif", Font.PLAIN, 14));
		clientName.setBounds(140, 60, 200, 25);
		clientName.setBackground(new Color(245, 245, 245));
		clientName.setForeground(Color.DARK_GRAY);
		clientName.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
		innerPanel.add(clientName);

		JLabel lblSoldProduct = createLabel("Products:", new Font("SansSerif", Font.PLAIN, 16), SwingConstants.LEFT, 25,
				120, 100, 25);
		lblSoldProduct.setForeground(Color.DARK_GRAY);
		innerPanel.add(lblSoldProduct);

		productListModel = new DefaultListModel<>();
		productList = new JList<>(productListModel);
		productList.setFont(new Font("SansSerif", Font.PLAIN, 14));
		productList.setBackground(new Color(245, 245, 245));
		productList.setForeground(Color.DARK_GRAY);

		JScrollPane scrollPane = new JScrollPane(productList);
		scrollPane.setBounds(140, 120, 200, 160);
		innerPanel.add(scrollPane);

		btnOk = new JButton("OK");
		btnOk.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnOk.setBounds(70, 310, 110, 30);
		btnOk.setBackground(new Color(76, 175, 80));
		btnOk.setForeground(Color.WHITE);
		btnOk.setBorder(BorderFactory.createEmptyBorder());
		btnOk.setFocusPainted(false);
		btnOk.addActionListener(this);
		innerPanel.add(btnOk);

		btnBack = new JButton("Back");
		btnBack.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnBack.setBounds(200, 310, 110, 30);
		btnBack.setBackground(new Color(244, 67, 54));
		btnBack.setForeground(Color.WHITE);
		btnBack.setBorder(BorderFactory.createEmptyBorder());
		btnBack.setFocusPainted(false);
		btnBack.addActionListener(this);
		innerPanel.add(btnBack);
	}

	private JLabel createLabel(String text, Font font, int alignment, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setHorizontalAlignment(alignment);
		label.setBounds(x, y, width, height);
		return label;
	}

	private void loadProducts() {
		productList.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 7179275671777238393L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);

				if (value instanceof Product) {
					Product product = (Product) value;
					label.setText(
							"Name: " + product.getName() + " - Price: " + product.getPublicPrice().getValue() + "€");
				}

				return label;
			}
		});

		List<Product> inventory = shop.getInventory();
		productListModel.clear();

		for (Product product : inventory) {
			productListModel.addElement(product);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnBack) {
			dispose();
		}
		if (e.getSource() == btnOk) {
			processSale();
		}
	}

	private void processSale() {
		String client = clientName.getText().trim();

		// Check if the name of client is empty
		if (client.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a client name.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		ArrayList<Product> selectedProducts = new ArrayList<>(productList.getSelectedValuesList());
		// Check if the list of products is empty
		if (selectedProducts.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please select at least one product.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		boolean sale = shop.makeSale(client, selectedProducts);
		if (sale) {
			JOptionPane.showMessageDialog(this, "Sale registered successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Fail adding a new sale", "Fail", JOptionPane.ERROR_MESSAGE);
		}
	}
}
