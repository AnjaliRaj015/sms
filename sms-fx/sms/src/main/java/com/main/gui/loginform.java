package com.main.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.main.utils.session;
import com.main.dao.userdao;
import com.main.model.user;

public class loginform extends Application {
    private TextField emailField;
    private PasswordField passwordField;
    private TextField passwordFieldVisible;
    private Button loginButton;
    private CheckBox rememberMeCheckbox;
    private Label forgotPasswordLabel;
    private Label signUpLabel;
    private Button showPasswordButton;
    private String role;

    public loginform(String role) {
        this.role = role;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Main layout container
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #d1d1e9;");

        // Title
        Label titleLabel = new Label("Hello");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        // Subtitle
        Label subtitleLabel = new Label("Welcome Back");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        // Form layout
        GridPane formLayout = new GridPane();
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setHgap(10);
        formLayout.setVgap(10);

        // Email field
        emailField = new TextField();
        emailField.setPromptText("Username");
        emailField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(emailField, 0, 0, 2, 1);

        // Password field
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        formLayout.add(passwordField, 0, 1, 1, 1);

        // Password field visible
        passwordFieldVisible = new TextField();
        passwordFieldVisible.setPromptText("Password");
        passwordFieldVisible.setStyle("-fx-background-color: #e0e4f2; -fx-padding: 10px;");
        passwordFieldVisible.setVisible(false);
        formLayout.add(passwordFieldVisible, 0, 1, 1, 1);

        // Show password button
        showPasswordButton = new Button("\uD83D\uDC41"); // Unicode for eye icon
        showPasswordButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #666;");
        formLayout.add(showPasswordButton, 1, 1);

        showPasswordButton.setOnAction(event -> {
            if (passwordField.isVisible()) {
                passwordFieldVisible.setText(passwordField.getText());
                passwordField.setVisible(false);
                passwordFieldVisible.setVisible(true);
            } else {
                passwordField.setText(passwordFieldVisible.getText());
                passwordField.setVisible(true);
                passwordFieldVisible.setVisible(false);
            }
        });

        // Remember Me checkbox
        rememberMeCheckbox = new CheckBox("Remember Me");
        rememberMeCheckbox.setStyle("-fx-text-fill: #666;");
        formLayout.add(rememberMeCheckbox, 0, 2);

        // Forgot Password label
        forgotPasswordLabel = new Label("Forgot Password?");
        forgotPasswordLabel.setStyle("-fx-text-fill: #666; -fx-underline: true;");
        formLayout.add(forgotPasswordLabel, 1, 2);
        GridPane.setMargin(forgotPasswordLabel, new Insets(0, 0, 0, 50));

        // Login button
        loginButton = new Button("LOGIN");
        loginButton.setStyle("-fx-background-color: #5e57e8; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold;");
        formLayout.add(loginButton, 0, 3, 2, 1);
        GridPane.setMargin(loginButton, new Insets(20, 0, 0, 0));

        // Sign Up label
        if ("customer".equals(role)) {
            HBox signUpBox = new HBox();
            signUpBox.setAlignment(Pos.CENTER);
            signUpBox.setSpacing(5);

            Label noAccountLabel = new Label("Don't have an account?");
            noAccountLabel.setStyle("-fx-text-fill: #666;");
            signUpLabel = new Label("Sign Up");
            signUpLabel.setStyle("-fx-text-fill: #5e57e8; -fx-underline: true;");

            signUpLabel.setOnMouseClicked(event -> {
                Stage registerStage = new Stage();
                new registerform().start(registerStage); // Assuming RegisterForm is a JavaFX class
                primaryStage.close();
            });

            signUpBox.getChildren().addAll(noAccountLabel, signUpLabel);
            mainLayout.getChildren().add(signUpBox);
        }

        mainLayout.getChildren().addAll(titleLabel, subtitleLabel, formLayout);

        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.isVisible() ? passwordField.getText() : passwordFieldVisible.getText();

            userdao userDAO = new userdao();
            user user = userDAO.getUser(email, password);
            if (user != null) {
                session.setLoggedInUser(user);
                mainform mainForm = new mainform(user);
                Stage newStage = new Stage();
                mainForm.show(newStage); // Call the method to show the main form
                primaryStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password");
                alert.showAndWait();
            }
        });

        Scene scene = new Scene(mainLayout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
