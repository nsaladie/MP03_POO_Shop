package view;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Employee;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userID;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JButton btnReset;

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

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(24, 39, 111, 37);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("User ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(73, 124, 96, 31);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(73, 208, 96, 30);
		panel.add(lblNewLabel_1_1);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			// Create a new object of the class Employee
			Employee worker = new Employee();
			boolean isLogin = false;

			// Get the data of the JText and the passwordField
			String identificationUser = userID.getText().trim();
			String password = String.valueOf(passwordField.getPassword()).trim();

			// Call a method that check if the user only pass numbers in the identification
			// User
			if (isNumeric(identificationUser)) {
				int user = Integer.parseInt(identificationUser);
				isLogin = worker.login(user, password);
			}

			if (isLogin) {
				// Create a new object of the class ShopView
				ShopView shopView = new ShopView();
				// Put visible the view of ShopView
				shopView.setVisible(true);
				// Close the view of LoginView
				dispose();
			} else {
				// Report to the user that only pass numeric numbers
				if (!isNumeric(identificationUser)) {
					JOptionPane.showMessageDialog(null, "Invalid UserId format");
				} else {
					// Report to the user that the credential are incorrect
					JOptionPane.showMessageDialog(null, "Incorrect  UserId or password");
				}

			}
		}

		if (e.getSource() == btnReset) {
			// Reset the text on userId and password
			userID.setText("");
			passwordField.setText("");
		}
	}

	public boolean isNumeric(String employeeId) {
		return employeeId.matches("[0-9]+");
	}
}
