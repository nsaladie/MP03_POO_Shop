package view;

import main.Shop;
import util.Constants;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ShopView extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Shop shop = new Shop();
	private JPanel contentPane;
	private JButton btnCashCount;
	private JButton btnAddProduct;
	private JButton btnAddStock;
	private JButton btnDeleteProduct;
	private JButton btnShowInventory;
	private JPanel panel;
	private JPanel panelTitle;
	private JLabel lblTitle;
	private JLabel lblImageShop;
	private JButton btnExportProduct;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopView frame = new ShopView();
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
	public ShopView() {
		shop.loadInventory();

		initializeFrame();
		initializeContentPane();
		initializePanel();
		initializePanelTitle();
		initializeMenuLabels();
		initializeButtons();
		initializeImage();
	}

	private void initializeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 500);
		// Get the screen size of the client computer
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Displays the shopView sale in the middle of the computer screen.
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		// Does not allow the user to change the size of the window
		setResizable(false);
	}

	private void initializeContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	private void initializePanel() {
		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(31, 34, 581, 400);
		contentPane.add(panel);
		panel.setLayout(null);
	}

	private void initializePanelTitle() {
		panelTitle = new JPanel();
		panelTitle.setBackground(new Color(176, 224, 230));
		panelTitle.setBounds(0, 0, 581, 65);
		panel.add(panelTitle);
		panelTitle.setLayout(null);

		lblTitle = new JLabel("MP05 - Shop");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblTitle.setBounds(10, 10, 253, 45);
		panelTitle.add(lblTitle);

		JLabel lblNameShop = new JLabel("MiTienda.com");
		lblNameShop.setForeground(Color.BLACK);
		lblNameShop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNameShop.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblNameShop.setBounds(282, 10, 289, 45);
		panelTitle.add(lblNameShop);
	}

	private void initializeMenuLabels() {
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblMenu.setBounds(10, 84, 276, 35);
		panel.add(lblMenu);
	}

	private void initializeButtons() {
		btnCashCount = new JButton("1. Cash Count");
		btnCashCount.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnCashCount.setBounds(38, 174, 205, 35);
		// Register if the user check this button
		btnCashCount.addActionListener(this);
		panel.add(btnCashCount);

		btnAddProduct = new JButton("2. Add Product");
		btnAddProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnAddProduct.setBounds(38, 219, 205, 35);
		// Register if the user check this button
		btnAddProduct.addActionListener(this);
		panel.add(btnAddProduct);

		btnAddStock = new JButton("3. Update Stock");
		btnAddStock.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnAddStock.setBounds(37, 264, 206, 35);
		// Register if the user check this button
		btnAddStock.addActionListener(this);
		panel.add(btnAddStock);

		btnDeleteProduct = new JButton("9. Delete Product");
		btnDeleteProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnDeleteProduct.setBounds(38, 354, 205, 35);
		// Register if the user check this button
		btnDeleteProduct.addActionListener(this);
		panel.add(btnDeleteProduct);

		btnShowInventory = new JButton("5. Show Inventory");
		btnShowInventory.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnShowInventory.setBounds(38, 309, 205, 35);
		panel.add(btnShowInventory);
		btnShowInventory.addActionListener(this);
		// Registering the current class (this) as a key listener for this component
		this.addKeyListener(this);
	}

	private void initializeImage() {
		lblImageShop = new JLabel("");
		lblImageShop.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageShop.setIcon(new ImageIcon(ShopView.class.getResource("/resorce/grow-shop.png")));
		lblImageShop.setBounds(296, 91, 271, 256);
		panel.add(lblImageShop);

		btnExportProduct = new JButton("0. Export Inventory");
		btnExportProduct.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnExportProduct.setBounds(38, 129, 205, 35);
		panel.add(btnExportProduct);
		btnExportProduct.addActionListener(this);

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

	private void opneInventoryView(int option) {
		// Call the InventoryView and pass the parameter of the shop
		InventoryView view = new InventoryView(shop);
		view.setLocation(getX() + getWidth() / 2, getY() + 50);
		// Set visible the view of InventoryView
		view.setVisible(true);
	}

	private void exportInventory() {
		boolean fileExport = shop.writeInventory();

		if (fileExport) {
			JOptionPane.showMessageDialog(this, "Inventory Successfully Exported", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Error Exporting Inventory", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnExportProduct) {
			exportInventory();
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
			opneInventoryView(Constants.SHOW_INVENTORY);
		}
		// If the user clicks on the btnDeleteProduct button, call the openProductView
		// method with Constants.DELETE_PRODUCT parameter
		if (e.getSource() == btnDeleteProduct) {
			openProductView(Constants.DELETE_PRODUCT);
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
			exportInventory();
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
			opneInventoryView(Constants.SHOW_INVENTORY);
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
