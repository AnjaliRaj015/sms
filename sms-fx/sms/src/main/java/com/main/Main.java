package com.main;

import com.main.gui.loginform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        showMainWindow();
    }

    public static void showMainWindow() {
        mainStage.setTitle("Service Management System - Role Selection");

        BorderPane mainLayout = new BorderPane();

        Label label = new Label("Select your role");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #666;");
        mainLayout.setTop(label);
        BorderPane.setAlignment(label, javafx.geometry.Pos.CENTER);

        HBox buttonPanel = new HBox(10);
        buttonPanel.setStyle("-fx-padding: 20;");
        buttonPanel.setAlignment(javafx.geometry.Pos.CENTER);

        Button staffButton = new Button("Staff");
        staffButton.setStyle("-fx-background-color: #5e57e8; -fx-text-fill: white; -fx-padding: 10px 20px;");
        Button customerButton = new Button("Customer");
        customerButton.setStyle("-fx-background-color: #5e57e8; -fx-text-fill: white; -fx-padding: 10px 20px;");

        staffButton.setOnAction(e -> {
            new loginform("staff").start(new Stage());
            mainStage.close();
        });

        customerButton.setOnAction(e -> {
            new loginform("customer").start(new Stage());
            mainStage.close();
        });

        buttonPanel.getChildren().addAll(staffButton, customerButton);
        mainLayout.setCenter(buttonPanel);

        Scene scene = new Scene(mainLayout, 400, 200);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
