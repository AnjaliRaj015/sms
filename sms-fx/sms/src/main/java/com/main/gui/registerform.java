package com.main.gui;

import com.main.dao.userdao;
import com.main.model.customer;
import com.main.model.user;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class registerform extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField addressField;
    private Button registerButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Register");

        // Main layout container
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #d1d1e9;");

        // Title
        Label titleLabel = new Label("Register");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        // Form layout
        GridPane formLayout = new GridPane();
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setHgap(10);
        formLayout.setVgap(10);

        // Name field
        Label nameLabel = new Label("Name:");
        formLayout.add(nameLabel, 0, 0);

        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(nameField, 1, 0);

        // Email field
        Label emailLabel = new Label("Email:");
        formLayout.add(emailLabel, 0, 1);

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(emailField, 1, 1);

        // Username field
        Label usernameLabel = new Label("Username:");
        formLayout.add(usernameLabel, 0, 2);

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(usernameField, 1, 2);

        // Password field
        Label passwordLabel = new Label("Password:");
        formLayout.add(passwordLabel, 0, 3);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(passwordField, 1, 3);

        // Phone field
        Label phoneLabel = new Label("Phone:");
        formLayout.add(phoneLabel, 0, 4);

        phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(phoneField, 1, 4);

        // Address field
        Label addressLabel = new Label("Address:");
        formLayout.add(addressLabel, 0, 5);

        addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(addressField, 1, 5);

        // Register button
        registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #5e57e8; -fx-text-fill: white; -fx-padding: 10px 20px;");
        registerButton.setOnAction(e -> handleRegister());
        GridPane.setColumnSpan(registerButton, 2);
        formLayout.add(registerButton, 0, 6);

        mainLayout.getChildren().addAll(titleLabel, formLayout);

        Scene scene = new Scene(mainLayout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        userdao userDAO = new userdao();
        user newUser = new user();
        customer newCustomer = new customer(username, password, name, email, phone, address);

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("customer");
        newUser.setPhone(phone);
        newUser.setAddress(address);

        userdao.addCustomer(newCustomer);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Success");
        alert.setHeaderText(null);
        alert.setContentText("Registration successful!");
        alert.showAndWait();

        new loginform("customer").start(new Stage());
        ((Stage) registerButton.getScene().getWindow()).close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
