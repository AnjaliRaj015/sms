package com.main.gui;

import com.main.dao.servicedao;
import com.main.model.service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class serviceform extends Stage {
    private TextField nameField;
    private TextField descriptionField;
    private TextField durationField;
    private TextField costField;
    private Button addButton;

    public serviceform() {
        setTitle("Add New Service");

        // Initialize GUI components
        nameField = createStyledTextField();
        descriptionField = createStyledTextField();
        durationField = createStyledTextField();
        costField = createStyledTextField();
        addButton = new Button("Add Service");

        // Set button style
        addButton.setStyle("-fx-background-color: #5e57e8; -fx-text-fill: white; -fx-padding: 10px 20px;");
        addButton.setOnAction(e -> handleAddService());

        // Set up layout and add components to the container
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #d1d1e9;");

        Label titleLabel = new Label("Add New Service");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setTextFill(Color.web("#333333"));

        vbox.getChildren().addAll(
                titleLabel,
                createLabeledField("Name:", nameField),
                createLabeledField("Description:", descriptionField),
                createLabeledField("Estimated Duration (in minutes):", durationField),
                createLabeledField("Cost:", costField),
                addButton
        );

        Scene scene = new Scene(vbox, 400, 500);
        setScene(scene);
        show();
    }

    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        textField.setPrefHeight(40);
        return textField;
    }

    private VBox createLabeledField(String labelText, TextField textField) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.web("#333333"));

        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(label, textField);
        return vBox;
    }

    private void handleAddService() {
        try {
            // Collect data from fields
            String name = nameField.getText();
            String description = descriptionField.getText();
            int estimatedDuration = Integer.parseInt(durationField.getText());
            double cost = Double.parseDouble(costField.getText());

            // Create a new service object using the parameterized constructor
            service newService = new service(name, description, estimatedDuration, cost);
            servicedao.addService(newService);

            // Provide feedback to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Service added successfully!");
            alert.showAndWait();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid numbers for duration and cost.");
            alert.showAndWait();
        }
    }

}
