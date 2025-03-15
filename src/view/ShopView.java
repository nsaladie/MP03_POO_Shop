package view;

import main.Shop;
import util.Constants;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ShopView extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private static Shop shop;
	private JPanel contentPane;
	private JButton btnCashCount;
	private JButton btnAddProduct;
	private JButton btnAddStock;
	private JButton btnDeleteProduct;
	private JButton btnShowInventory;
	private JButton btnSale;
	private JButton btnListSale;
	private JButton btnExportSale;
	private JPanel panel;
	private JPanel panelTitle;
	private JLabel lblTitle;
	private JButton btnExportProduct;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopView frame = new ShopView(shop);
					frame.setVisible(true);
					// Enable keyboard focus for the JFrame
					frame.setFocusable(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShopView(Shop shop) {
		ShopView.shop = shop;

		initializeFrame();
		initializeContentPane();
		initializePanel();
		initializePanelTitle();
		initializeMenuLabels();
		initializeButtons();

		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	private void initializeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setResizable(false);
		setTitle("MiTienda - Management");
	}

	private void initializeContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	private void initializePanel() {
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
		panel.setBounds(49, 30, 600, 450);
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(null);
	}

	private void initializePanelTitle() {
		panelTitle = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(135, 206, 250));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 10);
			}
		};
		panelTitle.setBounds(0, 0, 600, 70);
		panelTitle.setOpaque(false);
		panelTitle.setLayout(null);
		panel.add(panelTitle);

		lblTitle = new JLabel("Shop Management");
		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
		lblTitle.setBounds(15, 15, 400, 40);
		panelTitle.add(lblTitle);

		JLabel lblNameShop = new JLabel("MiTienda.com");
		lblNameShop.setForeground(Color.DARK_GRAY);
		lblNameShop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNameShop.setFont(new Font("SansSerif", Font.BOLD, 28));
		lblNameShop.setBounds(320, 15, 260, 40);
		panelTitle.add(lblNameShop);
	}

	private void initializeMenuLabels() {
		JLabel lblMenu = new JLabel("Main Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblMenu.setBounds(0, 90, 600, 35);
		lblMenu.setForeground(new Color(60, 63, 65));
		panel.add(lblMenu);
	}

	private void initializeButtons() {
		// Buttons on the right
		btnExportProduct = createStyledButton("0. Export Inventory", 50, 150);
		panel.add(btnExportProduct);
		btnExportProduct.addActionListener(this);

		btnAddProduct = createStyledButton("2. Add Product", 50, 210);
		panel.add(btnAddProduct);
		btnAddProduct.addActionListener(this);

		btnSale = createStyledButton("6. Make a Sale", 50, 270);
		panel.add(btnSale);
		btnSale.addActionListener(this);

		btnExportSale = createStyledButton("8. Export Sales", 50, 330);
		panel.add(btnExportSale);
		btnExportSale.addActionListener(this);

		// Buttons on the left
		btnCashCount = createStyledButton("1. Cash Count", 330, 150);
		panel.add(btnCashCount);
		btnCashCount.addActionListener(this);

		btnAddStock = createStyledButton("3. Update Stock", 330, 210);
		panel.add(btnAddStock);
		btnAddStock.addActionListener(this);

		btnShowInventory = createStyledButton("5. Show Inventory", 330, 270);
		panel.add(btnShowInventory);
		btnShowInventory.addActionListener(this);

		btnListSale = createStyledButton("7. Show Sales", 330, 330);
		panel.add(btnListSale);
		btnListSale.addActionListener(this);

		// Buttons on the center
		btnDeleteProduct = createStyledButton("9. Delete Product", 190, 390);
		panel.add(btnDeleteProduct);
		btnDeleteProduct.addActionListener(this);
	}

	private JButton createStyledButton(String text, int x, int y) {
		JButton button = new JButton(text);
		button.setFont(new Font("SansSerif", Font.PLAIN, 18));
		button.setBounds(x, y, 220, 50);
		button.setBackground(new Color(76, 175, 80));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(new Color(46, 125, 50), 2));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return button;
	}

	private void openCashView(int option) {
		// Call to the CashView and pass the parameters of shop and the option
		CashView cash = new CashView(shop, option);
		cash.setLocation(getX() + getWidth() / 2, getY() + 70);
		// Put visible the CashView
		cash.setVisible(true);
	}

	private void openProductView(int option) {
		// Call the view of ProductView, pass the parameters of shop and the option of
		// the menu (add, update, delete)
		ProductView view = new ProductView(shop, option);
		view.setLocation(getX() + getWidth() / 2, getY() + 50);
		// Put visible the ProductView
		view.setVisible(true);
	}

	private void openInventoryView(int option) {
		// Call the InventoryView and pass the parameter of the shop
		InventoryView view = new InventoryView(shop);
		view.setLocation(getX() + getWidth() / 2, getY() + 50);
		// Set visible the view of InventoryView
		view.setVisible(true);
	}

	private void exportInventory(int option) {
		boolean fileExport = shop.writeInventory();

		if (fileExport) {
			JOptionPane.showMessageDialog(this, "Inventory Successfully Exported", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Error Exporting Inventory", "Error", JOptionPane.ERROR_MESSAGE);
		}
		this.requestFocusInWindow();
	}

	private void openSale(int option) {
		SaleView view = new SaleView(shop);
		view.setLocation(getX() + getWidth() / 2, getY() + 50);
		view.setVisible(true);
	}

	public void openListSale(int option) {
		SalesListView view = new SalesListView(shop);
		view.setLocation(getX() + getWidth() / 2, getY() + 50);
		view.setVisible(true);
	}

	public void exportSale(int option) {
		boolean isExport = shop.exportSales();

		if (isExport) {
			JOptionPane.showMessageDialog(this, "Sales Successfully Exported", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Error Exporting Sales", "Error", JOptionPane.ERROR_MESSAGE);
		}
		this.requestFocusInWindow();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnExportProduct) {
			exportInventory(Constants.EXPORT_INVENTORY);
		}
		// If the user clicks on the btnCashCount button, call the openCashView method
		// with Constants.SHOW_CASH parameter
		if (e.getSource() == btnCashCount) {
			openCashView(Constants.SHOW_CASH);
		}
		// If the user clicks on the btnAddProduct button, call the openProductView
		// method with Constants.ADD_PRODUCT parameter
		if (e.getSource() == btnAddProduct) {
			openProductView(Constants.ADD_PRODUCT);
		}
		// If the user clicks on the btnAddStock button, call the openProductView method
		// with Constants.UPDATE_STOCK parameter
		if (e.getSource() == btnAddStock) {
			openProductView(Constants.UPDATE_STOCK);
		}
		// If the user clicks on the btnShowInventory button, call the opneInventoryView
		// method with Constants.SHOW_INVENTORY parameter
		if (e.getSource() == btnShowInventory) {
			openInventoryView(Constants.SHOW_INVENTORY);
		}
		// If the user clicks on the btnDeleteProduct button, call the openProductView
		// method with Constants.DELETE_PRODUCT parameter
		if (e.getSource() == btnDeleteProduct) {
			openProductView(Constants.DELETE_PRODUCT);
		}
		if (e.getSource() == btnSale) {
			openSale(Constants.MAKE_SALE);
		}

		if (e.getSource() == btnListSale) {
			openListSale(Constants.SHOW_SALE);
		}

		if (e.getSource() == btnExportSale) {
			exportSale(Constants.EXPORT_SALE);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();

		// Switch that calls the appropriate method depending on the key pressed by the
		// user
		switch (key) {
		case KeyEvent.VK_0:
			exportInventory(Constants.EXPORT_INVENTORY);
			break;
		case KeyEvent.VK_1:
			openCashView(Constants.SHOW_CASH);
			break;
		case KeyEvent.VK_2:
			openProductView(Constants.ADD_PRODUCT);
			break;
		case KeyEvent.VK_3:
			openProductView(Constants.UPDATE_STOCK);
			break;
		case KeyEvent.VK_5:
			openInventoryView(Constants.SHOW_INVENTORY);
			break;
		case KeyEvent.VK_6:
			openSale(Constants.MAKE_SALE);
			break;
		case KeyEvent.VK_7:
			openListSale(Constants.SHOW_SALE);
			break;
		case KeyEvent.VK_8:
			exportSale(Constants.EXPORT_SALE);
			break;
		case KeyEvent.VK_9:
			openProductView(Constants.DELETE_PRODUCT);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
