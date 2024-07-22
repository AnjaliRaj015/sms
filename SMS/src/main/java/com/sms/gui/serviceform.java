package main.java.com.sms.gui;

import main.java.com.sms.dao.servicedao;
import main.java.com.sms.model.service;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class serviceform extends JFrame {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField durationField;
    private JTextField costField;
    private JButton addButton;

    public serviceform() {
        // Initialize GUI components
        nameField = new JTextField(20);
        descriptionField = new JTextField(20);
        durationField = new JTextField(5);
        costField = new JTextField(10);
        addButton = new JButton("Add Service");

        // Set up layout and add components to the frame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("Estimated Duration (in minutes):"));
        add(durationField);
        add(new JLabel("Cost:"));
        add(costField);
        add(addButton);

        // Add action listener to the button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Collect data from fields
                String name = nameField.getText();
                String description = descriptionField.getText();
                int estimatedDuration = Integer.parseInt(durationField.getText());
                double cost = Double.parseDouble(costField.getText());

                // Create a new service object using the parameterized constructor
                service newService = new service(name, description, estimatedDuration, cost);
                servicedao.addService(newService);
                // Provide feedback to the user
                JOptionPane.showMessageDialog(null, "Service added successfully!");
            }
        });

        // Set up the frame
        setTitle("Add New Service");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new serviceform();
    }
}
