package com.main.gui;

import com.main.dao.userdao;
import com.main.model.customer;
import com.main.model.user;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class customerform extends Application {

    private TextField usernameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField addressField;
    private TextField passwordField;
    private TextField nameField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add New Customer");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #d1d1e9;");

        // Create and style labels and text fields
        nameField = createStyledTextField();
        usernameField = createStyledTextField();
        emailField = createStyledTextField();
        phoneField = createStyledTextField();
        addressField = createStyledTextField();
        passwordField = createStyledTextField();

        // Add fields to grid
        addLabeledFieldToGrid(grid, "Name:", nameField, 0);
        addLabeledFieldToGrid(grid, "Username:", usernameField, 1);
        addLabeledFieldToGrid(grid, "Email:", emailField, 2);
        addLabeledFieldToGrid(grid, "Phone:", phoneField, 3);
        addLabeledFieldToGrid(grid, "Address:", addressField, 4);
        addLabeledFieldToGrid(grid, "Password:", passwordField, 5);

        // Add Button
        Button addButton = new Button("Add Customer");
        addButton.setStyle("-fx-background-color: #5e57e8; -fx-text-fill: white; -fx-padding: 10px 20px;");
        addButton.setOnAction(e -> handleAddCustomer());
        GridPane.setConstraints(addButton, 1, 6);

        grid.getChildren().add(addButton);

        Scene scene = new Scene(grid, 450, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        textField.setPrefHeight(40);
        return textField;
    }

    private void addLabeledFieldToGrid(GridPane grid, String labelText, TextField textField, int rowIndex) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.web("#333333"));
        GridPane.setConstraints(label, 0, rowIndex);
        GridPane.setConstraints(textField, 1, rowIndex);
        grid.getChildren().addAll(label, textField);
    }

    private void handleAddCustomer() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();

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
        alert.setTitle("Customer Added");
        alert.setHeaderText(null);
        alert.setContentText("Customer added successfully!");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
