package main.java.com.sms.gui;

import main.java.com.sms.dao.userdao;
import main.java.com.sms.model.customer;
import main.java.com.sms.model.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registerform extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField AddressField;

    public registerform() {
        setTitle("Register");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel registerLabel = new JLabel("Register");
        registerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(registerLabel, gbc);

        JLabel nameLabel = new JLabel("Name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nameField, gbc);

        JLabel emailLabel = new JLabel("Email");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailField, gbc);

        JLabel usernameLabel = new JLabel("Username");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        JLabel phoneLabel = new JLabel("Phone");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(phoneField, gbc);

        JLabel AddressLabel = new JLabel("Address");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(AddressLabel, gbc);

        AddressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(AddressField, gbc);

        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 77;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String phone = new String(phoneField.getText());
                String address = new String(AddressField.getText());

                userdao userDAO = new userdao();
                user newUser = new user();
                customer newCustomer = new customer(username, password, name, email, phone, address);

                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setRole("customer");
                newUser.setPhone(phone);
                newUser.setAddress((address));

                userdao.addUser(newUser);
                userdao.addCustomer(newCustomer);

                JOptionPane.showMessageDialog(null, "Registration successful!");
                new loginform("customer");
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }
}
