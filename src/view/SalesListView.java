package view;

import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import javax.swing.text.*;
import main.Shop;
import model.Product;
import model.Sale;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class SalesListView extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel model;
	private ArrayList<Sale> sales;
	private JTable table;
	private static Shop shop;

	public SalesListView(Shop shop) {
		SalesListView.shop = shop;
		initializeUI();
		loadSales();
	}

	private void initializeUI() {
		setTitle("Sales List");
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblTitle = new JLabel("List of Sales", SwingConstants.CENTER);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
		contentPanel.add(lblTitle, BorderLayout.NORTH);

		setupTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		footerPanel.setBackground(Color.DARK_GRAY);
		JButton backButton = new JButton("Back");
		styleButton(backButton, Color.RED, Color.WHITE);
		backButton.addActionListener(e -> dispose());
		footerPanel.add(backButton);
		contentPanel.add(footerPanel, BorderLayout.SOUTH);
	}

	private void setupTable() {
		String[] columnNames = { "Client Name", "Product Name", "Price", "Date" };
		model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setBackground(new Color(245, 245, 245));
		table.setForeground(Color.DARK_GRAY);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CenteredMultiLineTableCellRenderer());
		table.setRowSelectionAllowed(false);
		table.setRowHeight(30);

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("SansSerif", Font.BOLD, 14));
		header.setBackground(new Color(100, 149, 237));
		header.setForeground(Color.WHITE);
		header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		header.setReorderingAllowed(false);
	}

	private class CenteredMultiLineTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JPanel panel = new JPanel(new GridBagLayout());
			JTextPane textPane = new JTextPane();

			textPane.setFont(table.getFont());
			textPane.setOpaque(false);
			textPane.setBorder(null);
			textPane.setEditable(false);

			StyledDocument doc = textPane.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);

			if (value != null) {
				textPane.setText(value.toString());
				doc.setParagraphAttributes(0, doc.getLength(), center, false);
			} else {
				textPane.setText("");
			}

			if (isSelected) {
				panel.setBackground(table.getSelectionBackground());
				textPane.setForeground(table.getSelectionForeground());
			} else {
				panel.setBackground(table.getBackground());
				textPane.setForeground(table.getForeground());
			}

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			panel.add(textPane, gbc);

			int preferredHeight = textPane.getPreferredSize().height + 10;
			if (preferredHeight > table.getRowHeight(row)) {
				table.setRowHeight(row, preferredHeight);
			}

			return panel;
		}
	}

	private void styleButton(JButton button, Color bgColor, Color fgColor) {
		button.setFont(new Font("SansSerif", Font.BOLD, 16));
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		button.setFocusPainted(false);
	}

	private void loadSales() {
		sales = shop.getSales();
		model.setRowCount(0);
		for (Sale sale : sales) {
			String clientName = sale.getClient().toString();
			StringBuilder productSelected = new StringBuilder();
			for (Product product : sale.getProducts()) {
				productSelected.append(product.getName()).append(" - ").append(product.getPublicPrice().getValue())
						.append("€\n");
			}
			if (productSelected.length() > 0) {
				productSelected.setLength(productSelected.length() - 1);
			}
			model.addRow(new Object[] { clientName, productSelected.toString(),
					sale.getAmount().getValue() + sale.getAmount().getCurrency(), sale.getDateSale() });
		}
		model.fireTableDataChanged();
	}
}
