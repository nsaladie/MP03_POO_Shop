package view;

import exception.LimitLoginException;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Employee;
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
	private JLabel lblSymbol;
	private JLabel lblSymbol2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
					frame.setTitle("Login");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 560);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Displays the shopView sale in the middle of the computer screen.
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(112, 45, 350, 400);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel Login = new JLabel("Login");
		Login.setHorizontalAlignment(SwingConstants.LEFT);
		Login.setFont(new Font("SansSerif", Font.BOLD, 30));
		Login.setBounds(24, 39, 111, 37);
		panel.add(Login);

		JLabel labelUser = new JLabel("User ID");
		labelUser.setFont(new Font("SansSerif", Font.PLAIN, 20));
		labelUser.setBounds(73, 124, 85, 31);
		panel.add(labelUser);

		JLabel labelPassword = new JLabel("Password");
		labelPassword.setFont(new Font("SansSerif", Font.PLAIN, 20));
		labelPassword.setBounds(73, 208, 91, 30);
		panel.add(labelPassword);

		// Create input userID
		userID = new JTextField();
		userID.setFont(new Font("SansSerif", Font.PLAIN, 18));
		userID.setBounds(73, 167, 213, 31);
		panel.add(userID);
		userID.setColumns(10);

		// Create input password
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		passwordField.setBounds(73, 248, 213, 30);
		panel.add(passwordField);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnLogin.setBounds(73, 329, 91, 31);
		btnLogin.addActionListener(this);
		panel.add(btnLogin);

		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnReset.setBounds(201, 329, 91, 31);
		btnReset.addActionListener(this);
		panel.add(btnReset);

		lblSymbol = new JLabel("*");
		lblSymbol.setForeground(Color.RED);
		lblSymbol.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblSymbol.setBounds(139, 124, 21, 26);
		panel.add(lblSymbol);

		lblSymbol2 = new JLabel("*");
		lblSymbol2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSymbol2.setForeground(Color.RED);
		lblSymbol2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblSymbol2.setBounds(160, 208, 21, 26);
		panel.add(lblSymbol2);

	}

	private void initialSesion() {
		// Create a new instance of the Employee class
		Employee worker = new Employee();
		boolean isLogin = false;

		// Get data from the UserID JTextField and the passwordField
		String identificationUser = userID.getText().trim();
		String password = String.valueOf(passwordField.getPassword()).trim();

		if (identificationUser.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "The userID or password can't be null", "Invalid Type: NULL",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			int user = Integer.parseInt(identificationUser);
			isLogin = worker.login(user, password);

			if (loginAttempts < Constants.MAX_LOGIN_ATTEMPTS) {
				if (isLogin) {
					// Create a new object of the class ShopView
					ShopView shopView = new ShopView();
					// Put visible the view of ShopView
					shopView.setVisible(true);
					// Put a title to the view
					shopView.setTitle("Menu - Shop");
					// Restore keyboard focus for the shop window
					shopView.requestFocus();
					// Close the view of LoginView
					dispose();

				} else {
					loginAttempts++;
					// Report to the user how many attempts are left
					JOptionPane.showMessageDialog(this, "Incorrect Login. " + loginAttempts + "/"
							+ Constants.MAX_LOGIN_ATTEMPTS + " attempts left.", "Fail Login",
							JOptionPane.ERROR_MESSAGE);
					resetFild();
				}
			} else {
				// If the employee has passed the 3 attempts, an exception is triggered.
				throw new LimitLoginException();
			}

		} catch (LimitLoginException maxAttempts) {
			JOptionPane.showMessageDialog(this, maxAttempts.getMessage(), "ERROR: Login", JOptionPane.ERROR_MESSAGE);
			dispose();
		} catch (NumberFormatException number) {
			JOptionPane.showMessageDialog(this, "Invalid UserId format", "Incorrect Type", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void resetFild() {
		// Reset the text on userId and password
		userID.setText("");
		passwordField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			initialSesion();
		}

		if (e.getSource() == btnReset) {
			resetFild();
		}
	}
}
