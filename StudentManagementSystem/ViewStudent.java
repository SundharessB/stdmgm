package StudentManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewStudent extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewStudent frame = new ViewStudent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewStudent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 782, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Use BorderLayout for the main contentPane
		contentPane.setLayout(new BorderLayout());

		JLabel lblNewLabel = new JLabel("Student Details");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));

		contentPane.add(lblNewLabel, BorderLayout.NORTH); // Add label to the top

		JButton btnNewButton = new JButton("Go Back");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBackToMenu();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnNewButton, BorderLayout.SOUTH); // Add button to the bottom

		// Fetch and display data from the database using JTable
		displayStudentDetails();

		// Create a JScrollPane to handle scrolling
		JScrollPane scrollPane = new JScrollPane(table);

		// Add scrollPane to the center of the contentPane
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Set the contentPane layout to BorderLayout
		setContentPane(contentPane);
	}

	private void displayStudentDetails() {
		// JDBC connection details
		String url = "jdbc:mysql://localhost:3308/studentmanagementsystem";
		String user = "root";
		String password = "root";

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			// SQL query to fetch student details
			String sql = "SELECT * FROM student";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
				 ResultSet resultSet = preparedStatement.executeQuery()) {

				// Create a DefaultTableModel
				DefaultTableModel model = new DefaultTableModel();

				// Add columns to the model
				model.addColumn("Name");
				model.addColumn("Entry Number");
				model.addColumn("Email");
				model.addColumn("Contact Number");
				model.addColumn("Home City");

				// Add rows to the model
				while (resultSet.next()) {
					String studentName = resultSet.getString("name");
					String entryNumber = resultSet.getString("entrynumber");
					String email = resultSet.getString("email");
					String contactNumber = resultSet.getString("contactnumber");
					String homeCity = resultSet.getString("homecity");

					model.addRow(new Object[]{studentName, entryNumber, email, contactNumber, homeCity});
				}

				// Create JTable with the model
				table = new JTable(model);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void goBackToMenu() {
		Menu menu = new Menu();
		menu.setVisible(true);
		dispose();
	}
}
