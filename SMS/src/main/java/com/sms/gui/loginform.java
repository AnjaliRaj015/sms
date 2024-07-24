package main.java.com.sms.gui;

import main.java.com.sms.dao.userdao;
import main.java.com.sms.model.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class loginform extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel welcomeLabel;
    private JLabel welcomeText;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JCheckBox rememberMeCheckbox;
    private JLabel forgotPasswordLabel;
    private JLabel signUpLabel;
    private JLabel logoLabel;
    private String role;

    public loginform(String role) {
        this.role = role;
        setTitle("Login");
        setSize(800, 400); // Adjusted size to accommodate new design
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(34, 34, 34)); // Background color similar to the image

        // Left panel for welcome message
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.BLACK); // Placeholder for background image
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Placeholder for logo
        ImageIcon logoIcon = new ImageIcon("D:\\projects\\sms\\SMS\\src\\main\\java\\com\\sms\\gui\\images\\login.jpg");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Welcome message
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeText = new JLabel("<html>Service Management System</html>");
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeText.setForeground(Color.WHITE);
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(logoLabel);
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(welcomeText);
        leftPanel.add(Box.createVerticalStrut(10));

        // Right panel for login form
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(new Color(50, 50, 50));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        JLabel signInLabel = new JLabel("SMS-Sign In");
        signInLabel.setFont(new Font("Arial", Font.BOLD, 24));
        signInLabel.setForeground(Color.WHITE);
        signInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(signInLabel, gbc);

        emailLabel = new JLabel("Username");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(emailLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(usernameField, gbc);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(passwordField, gbc);

        rememberMeCheckbox = new JCheckBox("Remember me");
        rememberMeCheckbox.setBackground(new Color(50, 50, 50));
        rememberMeCheckbox.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(rememberMeCheckbox, gbc);

        forgotPasswordLabel = new JLabel("Forgot password?");
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPasswordLabel.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        rightPanel.add(forgotPasswordLabel, gbc);

        loginButton = new JButton("Sign In");
        loginButton.setBackground(new Color(2, 20, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(loginButton, gbc);

        if ("customer".equals(role)) {
            signUpLabel = new JLabel("Don't have an account? Sign up");
            signUpLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            signUpLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            rightPanel.add(signUpLabel, gbc);

            signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    new registerform();
                    dispose();
                }
            });
        }

        // Split pane to divide left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5); // Adjust the resize weight as needed

        mainPanel.add(splitPane, BorderLayout.CENTER);
        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                userdao userDAO = new userdao();
                user user = userDAO.getUser(username, password);
                if (user != null) {
                    try {
                        new mainform(user);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        setVisible(true);
    }
}
