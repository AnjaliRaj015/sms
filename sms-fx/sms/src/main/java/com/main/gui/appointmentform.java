package com.main.gui;

import com.main.dao.appointmentdao;
import com.main.model.appointment;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class appointmentform extends Application {

    private TextArea appointmentTextArea;
    private Button loadAppointmentsButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Manage Appointments");

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        appointmentTextArea = new TextArea();
        appointmentTextArea.setEditable(false);

        ScrollPane scrollPane = new ScrollPane(appointmentTextArea);
        borderPane.setCenter(scrollPane);

        loadAppointmentsButton = new Button("Load Appointments");
        loadAppointmentsButton.setOnAction(e -> loadAppointments());

        HBox buttonPanel = new HBox(10);
        buttonPanel.setPadding(new Insets(10));
        buttonPanel.getChildren().add(loadAppointmentsButton);

        borderPane.setBottom(buttonPanel);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadAppointments() {
        List<appointment> appointments = appointmentdao.getAllAppointments();
        appointmentTextArea.setText("");
        for (appointment app : appointments) {
            appointmentTextArea.appendText("ID: " + app.getId() + " - Service ID: " + app.getServiceId() + " - Date: " + app.getDate() + " - Assigned Employee ID: " + app.getAssignedEmployeeId() + "\n");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
