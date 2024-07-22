package main.java.com.sms.gui;

import main.java.com.sms.dao.userdao;
import main.java.com.sms.model.customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageExistingCustomerForm extends JFrame {
    private JComboBox<customer> customerComboBox;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JButton saveButton;

    public ManageExistingCustomerForm() {
        setTitle("Manage Existing Customer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Select Customer:"));
        customerComboBox = new JComboBox<>();
        loadCustomers();
        add(customerComboBox);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        saveButton = new JButton("Save");
        add(saveButton);

        customerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customer selectedCustomer = (customer) customerComboBox.getSelectedItem();
                if (selectedCustomer != null) {
                    populateCustomerDetails(selectedCustomer);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomerDetails();
            }
        });

        setVisible(true);
    }

    private void loadCustomers() {
        List<customer> customers = userdao.getCustomers();
        for (customer cust : customers) {
            customerComboBox.addItem(cust);
        }
    }

    private void populateCustomerDetails(customer cust) {
        usernameField.setText(cust.getUsername());
        emailField.setText(cust.getEmail());
        phoneField.setText(cust.getPhone());
        addressField.setText(cust.getAddress());
    }

    private void saveCustomerDetails() {
        customer selectedCustomer = (customer) customerComboBox.getSelectedItem();
        if (selectedCustomer != null) {
            selectedCustomer.setUsername(usernameField.getText());
            selectedCustomer.setEmail(emailField.getText());
            selectedCustomer.setPhone(phoneField.getText());
            selectedCustomer.setAddress(addressField.getText());
            userdao.updateCustomer(selectedCustomer);
            JOptionPane.showMessageDialog(this, "Customer details updated successfully!");
        }
    }
}
