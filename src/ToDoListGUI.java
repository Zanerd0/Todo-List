import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToDoListGUI extends JFrame 
{
    private DefaultListModel<String> tasksListModel;
    private JList<String> tasksList;

    public ToDoListGUI() 
    {
        setTitle("To-do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton deleteAllButton = new JButton("Delete All");
        JButton editButton = new JButton("Edit");
        JButton exitButton = new JButton("Exit");
        JButton completeButton = new JButton("Mark as Completed");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteAllButton);
        buttonPanel.add(exitButton);
        

        // Create a panel for task list
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BorderLayout());

        tasksListModel = new DefaultListModel<>();
        tasksList = new JList<>(tasksListModel);
        JScrollPane scrollPane = new JScrollPane(tasksList);
        tasksPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for date label
        JPanel datePanel = new JPanel();
        JLabel dateLabel = new JLabel();
        datePanel.add(dateLabel);

        // Set layout for the main content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(tasksPanel, BorderLayout.CENTER);
        contentPane.add(datePanel, BorderLayout.NORTH);

        // Get the current date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = formatter.format(currentDate);
        dateLabel.setText("Date: " + formattedDate);

        // Add ActionListener for add button
        addButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String task = JOptionPane.showInputDialog(null, "Enter a new task:");
                if (task != null && !task.isEmpty()) 
                {
                    String newTask = (tasksListModel.size() + 1) + ". " + task + " (Incomplete)";
                    tasksListModel.addElement(newTask);
                }
            }
        });

        // Add ActionListener for delete button
        deleteButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int selectedIndex = tasksList.getSelectedIndex();
                if (selectedIndex != -1) 
                {
                    tasksListModel.remove(selectedIndex);
                }
            }
        });

        // Add ActionListener for delete all button
        deleteAllButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                tasksListModel.removeAllElements();
            }
        });

        // Add ActionListener for edit button
        editButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int selectedIndex = tasksList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedTask = tasksListModel.getElementAt(selectedIndex);
                    String[] parts = selectedTask.split("\\.", 2); // Split at the first occurrence of '.'
                    String editedTask = JOptionPane.showInputDialog(null, "Enter the edited task:", parts[1].trim());
                    if (editedTask != null && !editedTask.isEmpty()) 
                    {
                        String status = parts[1].substring(parts[1].indexOf("(")); // Extract the status
                        String newTask = parts[0].trim() + ". " + editedTask + " " + status; // Concatenate the edited task with the status
                        tasksListModel.set(selectedIndex, newTask);
                    }
                }
            }
        });



        // Add ActionListener for exit button
        exitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });

        // Add ActionListener for mark as completed button
        completeButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int selectedIndex = tasksList.getSelectedIndex();
                if (selectedIndex != -1) 
                {
                    String selectedTask = tasksListModel.getElementAt(selectedIndex);
                    String[] parts = selectedTask.split(" ");
                    parts[parts.length - 1] = "(Completed)";
                    String newTask = String.join(" ", parts);
                    tasksListModel.set(selectedIndex, newTask);
                }
            }
        });
    }

    public static void main(String[] args) 
    {
    	loginFrame frame=new loginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(570,100,410,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}
