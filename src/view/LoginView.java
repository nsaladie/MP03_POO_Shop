package view;

import exception.LimitLoginException;
import main.Shop;
import model.Employee;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userID;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JButton btnReset;
	private int loginAttempts = 0;
	private static Shop shop;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView(shop);
					frame.setVisible(true);
					frame.setTitle("Login - MiTienda");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginView(Shop shop) {
		LoginView.shop = shop;

		initializeLoginUI();
	}

	private void initializeLoginUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setResizable(false);
		setTitle("Login - MiTienda");

		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 45, 45));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 3902294183291571225L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.WHITE);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		panel.setBounds(40, 40, 400, 400);
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblLoginTitle = new JLabel("Login");
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
		lblLoginTitle.setForeground(new Color(50, 50, 50));
		lblLoginTitle.setBounds(120, 30, 150, 40);
		panel.add(lblLoginTitle);

		JLabel lblUserID = new JLabel("User ID");
		lblUserID.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblUserID.setForeground(new Color(80, 80, 80));
		lblUserID.setBounds(50, 110, 100, 25);
		panel.add(lblUserID);

		userID = new JTextField();
		userID.setFont(new Font("SansSerif", Font.PLAIN, 16));
		userID.setBounds(50, 140, 300, 35);
		userID.setBackground(new Color(245, 245, 245));
		userID.setForeground(Color.DARK_GRAY);
		userID.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		panel.add(userID);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblPassword.setForeground(new Color(80, 80, 80));
		lblPassword.setBounds(50, 190, 100, 25);
		panel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		passwordField.setBounds(50, 220, 300, 35);
		passwordField.setBackground(new Color(245, 245, 245));
		passwordField.setForeground(Color.DARK_GRAY);
		passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		panel.add(passwordField);

		btnLogin = createStyledButton("Login", new Color(76, 175, 80), 50, 300);
		btnLogin.addActionListener(this);
		panel.add(btnLogin);

		btnReset = createStyledButton("Reset", new Color(244, 67, 54), 210, 300);
		btnReset.addActionListener(this);
		panel.add(btnReset);
	}

	private JButton createStyledButton(String text, Color backgroundColor, int x, int y) {
		JButton button = new JButton(text);
		button.setFont(new Font("SansSerif", Font.BOLD, 16));
		button.setBounds(x, y, 120, 40);
		button.setBackground(backgroundColor);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return button;
	}

	private void initialSession() {
		Employee worker = new Employee();
		boolean isLogin = false;

		String identificationUser = userID.getText().trim();
		String password = String.valueOf(passwordField.getPassword()).trim();

		if (identificationUser.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "User ID or Password cannot be empty.", "Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			int user = Integer.parseInt(identificationUser);
			isLogin = worker.login(user, password);

			if (loginAttempts < Constants.MAX_LOGIN_ATTEMPTS) {
				if (isLogin) {
					ShopView shopView = new ShopView(shop);
					shopView.setVisible(true);
					shopView.setTitle("Menu - Shop");
					shopView.requestFocus();
					dispose();
				} else {
					loginAttempts++;
					JOptionPane.showMessageDialog(this, "Incorrect Login. " + loginAttempts + "/"
							+ Constants.MAX_LOGIN_ATTEMPTS + " attempts left.", "Login Failed",
							JOptionPane.ERROR_MESSAGE);
					resetFields();
				}
			} else {
				throw new LimitLoginException();
			}
		} catch (LimitLoginException maxAttempts) {
			JOptionPane.showMessageDialog(this, maxAttempts.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
			dispose();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid User ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void resetFields() {
		userID.setText("");
		passwordField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			initialSession();
		}
		if (e.getSource() == btnReset) {
			resetFields();
		}
	}
}
