import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class loginFrame extends JFrame implements ActionListener 
{
	Container container=getContentPane();
    JLabel userLabel=new JLabel("USERNAME");
    JLabel passwordLabel=new JLabel("PASSWORD");
    JTextField userTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JButton loginButton=new JButton("LOGIN");
    JButton signUpButton=new JButton("SIGNUP");
    JButton resetButton=new JButton("RESET");
    JCheckBox showPassword=new JCheckBox("Show Password");
	public void setLayoutManager()
	{
      
	   //Setting layout manager of Container to null
       container.setLayout(null);
	}
	
	
	public void setLocationAndSize()
	{
	       //Setting location and Size of each components using setBounds() method.
	       userLabel.setBounds(50,150,100,30);
	       passwordLabel.setBounds(50,220,100,30);
	       userTextField.setBounds(150,150,150,30);
	       passwordField.setBounds(150,220,150,30);
	       showPassword.setBounds(150,250,150,30);
	       loginButton.setBounds(27,300,100,30);
	       signUpButton.setBounds(149,300,100,30);
	       resetButton.setBounds(269,300,100,30);
	 
	}
	
	public void addComponentsToContainer()
	   {
	      //Adding each components to the Container
	       container.add(userLabel);
	       container.add(passwordLabel);
	       container.add(userTextField);
	       container.add(passwordField);
	       container.add(showPassword);
	       container.add(loginButton);
	       container.add(signUpButton);
	       container.add(resetButton);
	   }
    	
    loginFrame() //Creating constructor of LoginFrame() class
    {
    	setLayoutManager();
    	setLocationAndSize();
    	addComponentsToContainer();
    	
    	loginButton.addActionListener(this);
    	signUpButton.addActionListener(this);
        showPassword.addActionListener(this);
        resetButton.addActionListener(this);
    }
 
    //Overriding actionPerformed() method
    @Override
    public void actionPerformed(ActionEvent e) 
    {
    	if (e.getSource() == loginButton) 
    	{  
    		String username = userTextField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            if (checkLogin(username, password))
    		{
	    		ToDoListGUI todoListWindow = new ToDoListGUI();
	            todoListWindow.setVisible(true);
	            dispose();
    		}
            else 
            {
            	JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
    	}
    	else if (e.getSource() == signUpButton) 
    	{
            // Open the SignUpFrame window when the signUpButton is pressed
            signUpFrame signUpFrame = new signUpFrame();
            signUpFrame.setVisible(true);
    	}
        else if (e.getSource() == showPassword) 
    	{
            JCheckBox checkBox = (JCheckBox) e.getSource();
            passwordField.setEchoChar(checkBox.isSelected() ? '\u0000' : '\u2022');
    	}
    	else if (e.getSource() == resetButton) 
    	{
            userTextField.setText("");
            passwordField.setText("");
    	}
    }
    private boolean checkLogin(String username, String password) {
        String databaseUrl = "jdbc:mysql://localhost:3306/form"; // Replace with your database URL and name
        String dbUsername = "root"; // Replace with your MySQL username
        String dbPassword = "12345"; // Replace with your MySQL password

        try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM Users WHERE userName = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Check if any matching user is found
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while connecting to the database: " + ex.getMessage());
            // Handle any exceptions related to database connectivity or query execution.
        }
        return false;
    }
}