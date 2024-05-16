package view;

import main.Shop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CashView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panel;
	private JTextField cash;
	private Shop shop;
	private int option;

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
	public CashView(Shop shop, int option) {
		this.shop = shop;
		this.option = option;

		setBounds(100, 100, 360, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			panel = new JPanel();
			panel.setBounds(39, 21, 260, 260);
			contentPanel.add(panel);
			panel.setLayout(null);

			JLabel lblImage = new JLabel("");
			lblImage.setHorizontalAlignment(SwingConstants.CENTER);
			lblImage.setIcon(new ImageIcon(CashView.class.getResource("/resorce/caja-registradora.png")));
			lblImage.setBounds(0, 62, 260, 128);
			panel.add(lblImage);

			cash = new JTextField();
			// Put the label attribute to false, so that the text cannot be edited
			cash.setEditable(false);
			cash.setHorizontalAlignment(SwingConstants.CENTER);
			// In the label cash put the return of the method getCash of the class Shop
			cash.setText(shop.getCash());
			cash.setFont(new Font("SansSerif", Font.PLAIN, 20));
			cash.setBounds(64, 211, 133, 26);
			panel.add(cash);
			cash.setColumns(10);

			JLabel lblTitle = new JLabel("Total Cash");
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
			lblTitle.setBounds(0, 10, 260, 31);
			panel.add(lblTitle);
		}
	}
}
