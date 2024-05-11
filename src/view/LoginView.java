package view;

import exception.LimitLoginException;

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
	private static final int MAX_LOGIN_ATTEMPTS = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
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
		Login.setFont(new Font("Tahoma", Font.BOLD, 30));
		Login.setBounds(24, 39, 111, 37);
		panel.add(Login);

		JLabel labelUser = new JLabel("User ID");
		labelUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelUser.setBounds(73, 124, 96, 31);
		panel.add(labelUser);

		JLabel labelPassword = new JLabel("Password");
		labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelPassword.setBounds(73, 208, 96, 30);
		panel.add(labelPassword);

		// Create input userID
		userID = new JTextField();
		userID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		userID.setBounds(73, 167, 213, 31);
		panel.add(userID);
		userID.setColumns(10);

		// Create input password
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordField.setBounds(73, 248, 213, 30);
		panel.add(passwordField);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.setBounds(73, 329, 85, 31);
		btnLogin.addActionListener(this);
		panel.add(btnLogin);

		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReset.setBounds(201, 329, 85, 31);
		btnReset.addActionListener(this);
		panel.add(btnReset);

	}

	private void initialSesion() throws LimitLoginException {
		// Create a new instance of the Employee class
		Employee worker = new Employee();
		boolean isLogin = false;

		// Get data from the UserID JTextField and the passwordField
		String identificationUser = userID.getText().trim();
		String password = String.valueOf(passwordField.getPassword()).trim();

		// Check if the user entered only numeric characters in the UserID field
		if (isNumeric(identificationUser)) {
			int user = Integer.parseInt(identificationUser);
			isLogin = worker.login(user, password);

		} else {
			JOptionPane.showMessageDialog(this, "Invalid UserId format", "Incorrect Type", JOptionPane.ERROR_MESSAGE);
			// If the user use enter invalid user, exit the method
			return;
		}

		if (loginAttempts < MAX_LOGIN_ATTEMPTS) {
			if (isLogin) {
				// Create a new object of the class ShopView
				ShopView shopView = new ShopView();
				// Put visible the view of ShopView
				shopView.setVisible(true);
				// Restore keyboard focus for the shop window
				shopView.requestFocus();
				// Close the view of LoginView
				dispose();

			} else {
				loginAttempts++;
				// Report to the user that the credential are incorrect
				JOptionPane.showMessageDialog(this, "Incorrect  UserId or password", "ERROR: Login",
						JOptionPane.ERROR_MESSAGE);
				resetFild();

			}

		} else {
			throw new LimitLoginException("Maximum number of login attempts reached. The application will close");
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
			try {
				initialSesion();
			} catch (LimitLoginException error) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, error, "Fail Login", JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		}

		if (e.getSource() == btnReset) {
			resetFild();
		}
	}

	public boolean isNumeric(String employeeId) {
		return employeeId.matches("[0-9]+");
	}
}
