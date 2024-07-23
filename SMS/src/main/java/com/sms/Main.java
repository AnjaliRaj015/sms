package main.java.com.sms;

import main.java.com.sms.gui.loginform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.sms.db.database;

public class Main {
    public static void main(String[] args) {
        database.connect();
        new RoleSelectionForm();
    }
}

class RoleSelectionForm extends JFrame {
    public RoleSelectionForm() {
        setTitle("Service Management System - Role Selection");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Select your role", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton staffButton = new JButton("Staff");
        JButton customerButton = new JButton("Customer");

        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new loginform("staff");
                dispose();
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new loginform("customer");
                dispose();
            }
        });

        buttonPanel.add(staffButton);
        buttonPanel.add(customerButton);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
