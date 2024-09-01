import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class signUpFrame extends JFrame 
{
    private JTextField firstNameTextField, lastNameTextField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    // Constructor for SignUpFrame
    public signUpFrame() {
        setTitle("Sign Up Form");
        setSize(310, 250);
        setLocationRelativeTo(null);

        // Create components for the sign-up window
        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameTextField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        signUpButton = new JButton("Sign Up");

        // Add components to the container
        container.add(firstNameLabel);
        container.add(firstNameTextField);
        container.add(lastNameLabel);
        container.add(lastNameTextField);
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(signUpButton);

        // Add ActionListener for the signUpButton
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                char[] passwordChars = passwordField.getPassword();

                // Insert the user data into the database
                insertUserData(firstName, lastName, passwordChars);
            }
        });
    }

    // Method to insert user details into the MySQL database
    private void insertUserData(String firstName, String lastName, char[] passwordChars) {
        String databaseUrl = "jdbc:mysql://localhost:3306/form"; // Replace with your database URL and name
        String dbUsername = "root"; // Replace with your MySQL username
        String dbPassword = "12345"; // Replace with your MySQL password

        // Convert the password char array to a String
        String password = new String(passwordChars);

        try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword)) {
            String query = "INSERT INTO Users (userId, userName, firstName, lastName, password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) 
            {
            	preparedStatement.setInt(1, generateUserId(connection));
                preparedStatement.setString(2, generateUserName(firstName, lastName));
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5, password);
                preparedStatement.executeUpdate();
                System.out.println("User signed up successfully!");
                dispose();

            }
        } catch (SQLException ex) {
            System.out.println("Error while connecting to the database: " + ex.getMessage());
            // Handle any exceptions related to database connectivity or query execution.
        }
    }

    // Method to generate the userName based on first name and last name
    private String generateUserName(String firstName, String lastName) 
    {
        return firstName.toLowerCase() + "_" + lastName.toLowerCase();
    }
    
    private int generateUserId(Connection connection) throws SQLException {
        // Your implementation here...
        // You can use a database sequence, UUID, or any other method to generate a unique ID.
        // For simplicity, let's use a simple counter that increments on each sign-up.
        // Note: In practice, you should use a more robust and scalable method.
        return getNextUserIdFromDatabase(connection);
    }

    private int getNextUserIdFromDatabase(Connection connection) throws SQLException {
        int nextUserId = 1;
        String query = "SELECT MAX(userId) FROM Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nextUserId = resultSet.getInt(1) + 1;
            }
        }
        return nextUserId;
    }
}
