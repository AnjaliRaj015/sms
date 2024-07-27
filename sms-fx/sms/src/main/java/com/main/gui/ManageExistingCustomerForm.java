package com.main.gui;

import com.main.dao.userdao;
import com.main.model.customer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class ManageExistingCustomerForm extends Application {
    private ComboBox<customer> customerComboBox;
    private TextField usernameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField addressField;
    private Button saveButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Manage Existing Customer");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(15);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #d1d1e9;");

        // Create and style labels and fields
        customerComboBox = new ComboBox<>();
        customerComboBox.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        loadCustomers();

        usernameField = createStyledTextField();
        emailField = createStyledTextField();
        phoneField = createStyledTextField();
        addressField = createStyledTextField();

        // Add fields to grid
        addLabeledFieldToGrid(gridPane, "Select Customer:", customerComboBox, 0);
        addLabeledFieldToGrid(gridPane, "Username:", usernameField, 1);
        addLabeledFieldToGrid(gridPane, "Email:", emailField, 2);
        addLabeledFieldToGrid(gridPane, "Phone:", phoneField, 3);
        addLabeledFieldToGrid(gridPane, "Address:", addressField, 4);

        // Save Button
        saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #5e57e8; -fx-text-fill: white; -fx-padding: 10px 20px;");
        saveButton.setOnAction(e -> saveCustomerDetails());
        GridPane.setConstraints(saveButton, 1, 5);

        gridPane.getChildren().add(saveButton);

        customerComboBox.setOnAction(e -> {
            customer selectedCustomer = customerComboBox.getValue();
            if (selectedCustomer != null) {
                populateCustomerDetails(selectedCustomer);
            }
        });

        Scene scene = new Scene(gridPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        textField.setPrefHeight(40);
        return textField;
    }

    private void addLabeledFieldToGrid(GridPane grid, String labelText, Control field, int rowIndex) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.web("#333333"));
        GridPane.setConstraints(label, 0, rowIndex);
        GridPane.setConstraints(field, 1, rowIndex);
        grid.getChildren().addAll(label, field);
    }

    private void loadCustomers() {
        List<customer> customers = userdao.getCustomers();
        ObservableList<customer> customerList = FXCollections.observableArrayList(customers);
        customerComboBox.setItems(customerList);
    }

    private void populateCustomerDetails(customer cust) {
        usernameField.setText(cust.getUsername());
        emailField.setText(cust.getEmail());
        phoneField.setText(cust.getPhone());
        addressField.setText(cust.getAddress());
    }

    private void saveCustomerDetails() {
        customer selectedCustomer = customerComboBox.getValue();
        if (selectedCustomer != null) {
            selectedCustomer.setUsername(usernameField.getText());
            selectedCustomer.setEmail(emailField.getText());
            selectedCustomer.setPhone(phoneField.getText());
            selectedCustomer.setAddress(addressField.getText());
            userdao.updateCustomer(selectedCustomer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Customer details updated successfully!");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
