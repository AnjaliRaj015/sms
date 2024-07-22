package main.java.com.sms.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class staffForm extends JFrame {
    public staffForm() {
        setTitle("Staff Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 165, 25);
        panel.add(nameText);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 50, 80, 25);
        panel.add(roleLabel);

        JTextField roleText = new JTextField(20);
        roleText.setBounds(150, 50, 165, 25);
        panel.add(roleText);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(10, 80, 80, 25);
        panel.add(contactLabel);

        JTextField contactText = new JTextField(20);
        contactText.setBounds(150, 80, 165, 25);
        panel.add(contactText);

        JButton addButton = new JButton("Add Staff");
        addButton.setBounds(10, 110, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle adding staff logic here
            }
        });
    }
}
