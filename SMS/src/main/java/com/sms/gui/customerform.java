package main.java.com.sms.gui;

import main.java.com.sms.dao.userdao;
import main.java.com.sms.model.customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class customerform extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField passwordField;

    public customerform() {
        setTitle("Add New Customer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

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

        add(new JLabel("Password:"));
        passwordField = new JTextField();
        add(passwordField);

        JButton addButton = new JButton("Add Customer");
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();
                String password = passwordField.getText();

                customer newCustomer = new customer(username, password, address);
                newCustomer.setEmail(email);
                newCustomer.setPhone(phone);
                userdao.addCustomer(newCustomer);
                JOptionPane.showMessageDialog(customerform.this, "Customer added successfully!");
                dispose();
            }
        });

        setVisible(true);
    }
}
