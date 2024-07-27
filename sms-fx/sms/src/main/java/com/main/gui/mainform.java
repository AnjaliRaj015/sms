package com.main.gui;

import com.main.dao.servicedao;
import com.main.dao.servicehistorydao;
import com.main.Main;
import com.main.dao.QuoteRequestDAO;
import com.main.dao.appointmentdao;
import com.main.dao.reportdao;
import com.main.dao.userdao;
import com.main.db.database;
import com.main.model.service;
import com.main.model.serviceHistory;
import com.main.model.staff;
import com.main.model.user;
import com.main.model.QuoteRequest;
import com.main.model.appointment;
import com.main.model.customer;
import com.main.utils.session;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class mainform {
    private user user;

    public mainform(user user) {
        this.user = user;
    }

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Service Management System");

        TabPane tabPane = new TabPane();

        if (user.getRole().equals("staff")) {
            tabPane.getTabs().addAll(
                    createHomeTab(),
                    createServiceManagementTab(),
                    createAppointmentManagementTab(),
                    createCustomerManagementTab(),
                    createIncomingRequestsTab());
        } else if (user.getRole().equals("customer")) {
            tabPane.getTabs().addAll(
                    createServiceTab(),
                    createQuoteRequestTab(),
                    createAppointmentsTab(),
                    createPendingQuoteReqTab(),
                    createCompletedQuoteReqTab());
        }

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        // Adding logout button and setting its action
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> {
            Main.showMainWindow(); // Redirect to the main application window
            primaryStage.close();
        });

        VBox vbox = new VBox(logoutButton);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        root.setBottom(vbox);
        String css = getClass().getResource("styles.css").toExternalForm();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(css); 
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // ---------------------------------------------------STAFF FUNCTIONS-----------------------------------------//

    // HOME-TAB-STAFF
    private Tab createHomeTab() {
        Tab homeTab = new Tab("Home");
        homeTab.setClosable(false);

        BorderPane homePane = new BorderPane();

        TableView<Map.Entry<String, Double>> revenueTable = createRevenueTable();
        TableView<Map.Entry<String, Integer>> workloadTable = createWorkloadTable();

        TabPane reportTabPane = new TabPane();
        reportTabPane.getTabs().addAll(
                new Tab("Revenue Report", new ScrollPane(revenueTable)),
                new Tab("Workload Distribution", new ScrollPane(workloadTable)));

        homePane.setCenter(reportTabPane);
        homeTab.setContent(homePane);

        return homeTab;
    }

    private TableView<Map.Entry<String, Double>> createRevenueTable() {
        TableView<Map.Entry<String, Double>> tableView = new TableView<>();
        TableColumn<Map.Entry<String, Double>, String> serviceColumn = new TableColumn<>("Service Type");
        TableColumn<Map.Entry<String, Double>, Double> revenueColumn = new TableColumn<>("Total Revenue");

        serviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        revenueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        tableView.getColumns().addAll(serviceColumn, revenueColumn);

        try {
            reportdao dao = new reportdao(database.getConnection()); // Assuming database.getConnection() returns a
                                                                     // valid Connection
            Map<String, Double> revenueData = dao.getRevenueByServiceType();
            tableView.setItems(FXCollections.observableArrayList(revenueData.entrySet()));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions properly, maybe show an error dialog to the user
        }

        return tableView;
    }

    private TableView<Map.Entry<String, Integer>> createWorkloadTable() {
        TableView<Map.Entry<String, Integer>> tableView = new TableView<>();
        TableColumn<Map.Entry<String, Integer>, String> staffColumn = new TableColumn<>("Staff Member");
        TableColumn<Map.Entry<String, Integer>, Integer> workloadColumn = new TableColumn<>("Workload");

        staffColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        workloadColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        tableView.getColumns().addAll(staffColumn, workloadColumn);

        try {
            reportdao dao = new reportdao(database.getConnection()); // Assuming database.getConnection() returns a
                                                                     // valid Connection
            Map<String, Integer> workloadData = dao.getWorkloadDistribution();
            tableView.setItems(FXCollections.observableArrayList(workloadData.entrySet()));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions properly, maybe show an error dialog to the user
        }

        return tableView;
    }

    // SERVICE MANAGEMENT-TAB-STAFF
    private Tab createServiceManagementTab() {
        Tab serviceTab = new Tab("Service Management");
        serviceTab.setClosable(false);

        BorderPane servicePane = new BorderPane();

        // Create UI elements
        Label serviceLabel = new Label("Service Management");
        TextArea serviceTextArea = new TextArea();
        Button loadServicesButton = new Button("Load Services");
        Button addServiceButton = new Button("Add New Service");

        // Setup TextArea
        serviceTextArea.setEditable(false);

        // Add Event Handlers
        loadServicesButton.setOnAction(e -> {
            List<service> services = servicedao.getAllServices();
            serviceTextArea.clear();
            for (service svc : services) {
                serviceTextArea.appendText(svc.getName() + " - " + svc.getDescription() + " - " + svc.getCost() + "\n");
            }
        });

        addServiceButton.setOnAction(e -> new serviceform()); // Show form to add new service

        // Layout setup
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(loadServicesButton, addServiceButton);

        servicePane.setTop(serviceLabel);
        servicePane.setCenter(new ScrollPane(serviceTextArea));
        servicePane.setBottom(buttonBox);

        serviceTab.setContent(servicePane);
        return serviceTab;
    }

    // APPOINMENT MANAGEMENT TAB-STAFF
    private Tab createAppointmentManagementTab() {
        Tab appointmentTab = new Tab("Appointment Management");
        appointmentTab.setClosable(false);

        BorderPane appointmentPane = new BorderPane();

        Label headerLabel = new Label("Appointment Management");
        TextArea appointmentTextArea = new TextArea();
        Button loadAppointmentsButton = new Button("Load Appointments");
        Button markCompletedButton = new Button("Mark as Completed");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, appointmentTextArea, loadAppointmentsButton, markCompletedButton);

        appointmentPane.setCenter(vbox);

        loadAppointmentsButton.setOnAction(e -> {
            appointmentdao appointmentDAO = new appointmentdao();
            userdao userDAO = new userdao();
            int staffId = userDAO.getStaffIdByUserId(user.getId());

            List<appointment> appointments = appointmentDAO.getAppointmentsByStaffId(staffId);
            appointmentTextArea.setText(""); // Clear previous data
            for (appointment appointment : appointments) {
                appointmentTextArea.appendText("Service ID: " + appointment.getServiceId() +
                        " - Customer ID: " + appointment.getCustomerId() +
                        " - Date: " + appointment.getDate() +
                        " - Time: " + appointment.getTime() + "\n");
            }
        });
        markCompletedButton.setOnAction(e -> {
            TextField appointmentIdField = new TextField();
            TextField costField = new TextField();
            Label costLabel = new Label("Cost:");

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Mark Appointment as Completed");
            dialog.setHeaderText(null);

            VBox dialogContent = new VBox(10);
            dialogContent.setPadding(new Insets(10));
            dialogContent.getChildren().addAll(new Label("Appointment ID:"), appointmentIdField, costLabel, costField);

            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int appointmentId = Integer.parseInt(appointmentIdField.getText());
                    double cost = Double.parseDouble(costField.getText());

                    appointmentdao appointmentDAO = new appointmentdao();
                    QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
                    servicehistorydao serviceHistoryDAO = new servicehistorydao();
                    appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
                    if (appointment != null) {
                        appointment.setStatus("completed");
                        appointmentDAO.updateAppointment(appointment);
                        String serviceName = appointmentDAO.getServiceNameById(appointment.getServiceId());

                        serviceHistory history = new serviceHistory();
                        history.setCustomerId(appointment.getCustomerId());
                        history.setServiceName(serviceName);
                        history.setDate((Date) appointment.getDate());
                        history.setCost(cost);

                        serviceHistoryDAO.addServiceHistory(history);

                        showAlert(Alert.AlertType.INFORMATION, "Success",
                                "Appointment marked as completed and service history updated!");

                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Appointment not found!");
                    }
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid input format!");
                }
            }
        });

        appointmentTab.setContent(appointmentPane);
        return appointmentTab;
    }

    // CUSTOMER MANAGEMENT TAB-STAFF
    private Tab createCustomerManagementTab() {
        Tab customerTab = new Tab("Customer Management");
        customerTab.setClosable(false);

        BorderPane customerPane = new BorderPane();

        Label headerLabel = new Label("Customer Management");
        TextArea customerTextArea = new TextArea();
        Button addCustomerButton = new Button("Add New Customer");
        Button manageCustomerButton = new Button("Manage Existing Customer");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, customerTextArea, addCustomerButton, manageCustomerButton);

        customerPane.setCenter(vbox);

        addCustomerButton.setOnAction(e -> {
            new customerform().start(new Stage()); // Show form to add new customer
        });

        manageCustomerButton.setOnAction(e -> {
            new ManageExistingCustomerForm().start(new Stage()); // Show form to manage existing customers
        });

        userdao userDAO = new userdao();
        List<customer> customers = userDAO.getCustomers(); // Assuming userdao is used for customer data
        customerTextArea.setText(""); // Clear previous data
        for (customer c : customers) {
            customerTextArea.appendText(c.getUsername() + " - " + c.getEmail() + "\n");
        }

        customerTab.setContent(customerPane);
        return customerTab;
    }

    // INCOMING REQUEST MANAGEMENT TAB
    private Tab createIncomingRequestsTab() {
        Tab incomingRequestsTab = new Tab("Incoming Requests");
        incomingRequestsTab.setClosable(false);

        BorderPane incomingRequestsPane = new BorderPane();

        Label headerLabel = new Label("Incoming Requests");
        TextArea incomingRequestTextArea = new TextArea();
        Button loadIncomingRequestsButton = new Button("Load Incoming Requests");
        Button takeRequestButton = new Button("Take Request");
        Button rejectRequestButton = new Button("Reject Request");
        Button assignRequestButton = new Button("Assign Request");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, incomingRequestTextArea,
                loadIncomingRequestsButton, takeRequestButton,
                rejectRequestButton, assignRequestButton);

        incomingRequestsPane.setCenter(vbox);

        loadIncomingRequestsButton.setOnAction(e -> {
            QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
            List<QuoteRequest> quoteRequests = quoteRequestDAO.getPendingQuoteRequests();
            incomingRequestTextArea.setText("");
            for (QuoteRequest quoteRequest : quoteRequests) {
                incomingRequestTextArea.appendText(
                        "ID: " + quoteRequest.getId() +
                                " | Customer: " + quoteRequest.getCustomerName() +
                                " | Service: " + quoteRequest.getServiceName() +
                                " | Address: " + quoteRequest.getCustomerAddress() +
                                " | Phone: " + quoteRequest.getCustomerPhone() + "\n");
            }
        });

        takeRequestButton.setOnAction(e -> handleRequest("accepted"));
        rejectRequestButton.setOnAction(e -> handleRequest("rejected"));
        assignRequestButton.setOnAction(e -> assignRequestToEmployee());

        incomingRequestsTab.setContent(incomingRequestsPane);
        return incomingRequestsTab;
    }

    private void handleRequest(String action) {
        TextField requestIdField = new TextField();
        TextField dateField = new TextField();
        Label dateLabel = action.equals("accepted") ? new Label("Appointment Date (yyyy-mm-dd):") : null;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(action.equals("accepted") ? "Take Request" : "Reject Request");
        dialog.setHeaderText(null);

        VBox dialogContent = new VBox(10);
        dialogContent.setPadding(new Insets(10));
        dialogContent.getChildren().addAll(new Label("Request ID:"), requestIdField);

        if (dateLabel != null) {
            dialogContent.getChildren().addAll(dateLabel, dateField);
        }

        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int requestId = Integer.parseInt(requestIdField.getText());
                String dateStr = dateField.getText();
                java.sql.Date appointmentDate = action.equals("accepted") ? java.sql.Date.valueOf(dateStr) : null;

                QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
                List<QuoteRequest> quoteRequests = quoteRequestDAO.getPendingQuoteRequests();
                QuoteRequest selectedRequest = quoteRequests.stream()
                        .filter(qr -> qr.getId() == requestId)
                        .findFirst()
                        .orElse(null);

                if (selectedRequest != null) {
                    if (action.equals("accepted")) {
                        appointment newAppointment = new appointment();
                        newAppointment.setServiceId(selectedRequest.getServiceId());
                        newAppointment.setCustomerId(selectedRequest.getCustomerId());

                        userdao userDAO = new userdao();
                        int staffId = userDAO.getStaffIdByUserId(user.getId());

                        newAppointment.setStaffId(staffId);
                        newAppointment.setDate(appointmentDate);
                        newAppointment.setTime("09:00:00");
                        newAppointment.setStatus("scheduled");

                        appointmentdao appointmentDAO = new appointmentdao();
                        appointmentDAO.addAppointment(newAppointment);

                        selectedRequest.setStatus("accepted");
                        quoteRequestDAO.updateQuoteRequest(selectedRequest);

                        showAlert(Alert.AlertType.INFORMATION, "Success",
                                "Request accepted and appointment scheduled!");

                    } else if (action.equals("rejected")) {
                        selectedRequest.setStatus("rejected");
                        quoteRequestDAO.updateQuoteRequest(selectedRequest);

                        showAlert(Alert.AlertType.INFORMATION, "Success", "Request rejected!");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid Request ID!");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Request ID format!");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid date format!");
            }
        }
    }

    private void assignRequestToEmployee() {
        TextField requestIdField = new TextField();
        TextField dateField = new TextField();
        ComboBox<staff> staffComboBox = new ComboBox<>();
        userdao userDAO = new userdao();
        List<staff> staffList = userDAO.getStaff();
        staffComboBox.getItems().addAll(staffList);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Assign Request");
        dialog.setHeaderText(null);

        VBox dialogContent = new VBox(10);
        dialogContent.setPadding(new Insets(10));
        dialogContent.getChildren().addAll(new Label("Request ID:"), requestIdField,
                new Label("Appointment Date (yyyy-mm-dd):"), dateField,
                new Label("Assign to Staff:"), staffComboBox);

        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int requestId = Integer.parseInt(requestIdField.getText());
            String dateStr = dateField.getText();
            staff selectedStaff = staffComboBox.getValue();

            QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
            List<QuoteRequest> quoteRequests = quoteRequestDAO.getPendingQuoteRequests();
            QuoteRequest selectedRequest = quoteRequests.stream()
                    .filter(qr -> qr.getId() == requestId)
                    .findFirst()
                    .orElse(null);

            if (selectedRequest != null) {
                appointment newAppointment = new appointment();
                newAppointment.setServiceId(selectedRequest.getServiceId());
                newAppointment.setCustomerId(selectedRequest.getCustomerId());
                newAppointment.setStaffId(selectedStaff.getId());
                newAppointment.setDate(java.sql.Date.valueOf(dateStr));
                newAppointment.setTime("09:00:00");
                newAppointment.setStatus("scheduled");

                appointmentdao appointmentDAO = new appointmentdao();
                appointmentDAO.addAppointment(newAppointment);

                selectedRequest.setStatus("accepted");
                quoteRequestDAO.updateQuoteRequest(selectedRequest);

                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Request assigned to " + selectedStaff.getName() + " and appointment scheduled!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Request ID!");
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ---------------------------------------------------CUSTOMER FUNCTIONS-----------------------------------------//

    // BROWSE SERVICES-CUSTOMER TAB
    private Tab createServiceTab() {
        Tab serviceTab = new Tab("Services");
        serviceTab.setClosable(false);

        // Create the main layout for the tab
        BorderPane servicePane = new BorderPane();

        // Create the UI components
        Label headerLabel = new Label("Available Services");
        TextArea serviceTextArea = new TextArea();
        serviceTextArea.setPrefSize(500, 400); // Set preferred size for the TextArea
        Button loadServicesButton = new Button("Load Services");

        // Set up the button action
        loadServicesButton.setOnAction(event -> {
            servicedao serviceDAO = new servicedao(); // Assuming ServiceDAO is your data access object
            List<service> services = serviceDAO.getAllServices();
            serviceTextArea.clear();
            for (service service : services) {
                serviceTextArea.appendText(
                        service.getName() + " - " + service.getDescription() + " - " + service.getCost() + "\n");
            }
        });

        // Set up the layout
        VBox vbox = new VBox(10); // Spacing between elements
        vbox.setPadding(new Insets(10)); // Padding around the VBox
        vbox.getChildren().addAll(headerLabel, serviceTextArea, loadServicesButton);

        servicePane.setCenter(vbox);

        // Set the content of the tab
        serviceTab.setContent(servicePane);
        return serviceTab;
    }

    // CREATE QUOTES-CUSTOMER TAB
    private Tab createQuoteRequestTab() {
        Tab quoteRequestTab = new Tab("Quote Requests");
        quoteRequestTab.setClosable(false);

        BorderPane quoteRequestPane = new BorderPane();

        Label headerLabel = new Label("Request a Quote");
        ComboBox<service> serviceComboBox = new ComboBox<>();
        Button requestQuoteButton = new Button("Request Quote");

        servicedao serviceDAO = new servicedao(); // Assuming ServiceDAO is your data access object
        List<service> services = serviceDAO.getAllServices();
        serviceComboBox.getItems().addAll(services);

        requestQuoteButton.setOnAction(event -> {
            service selectedService = serviceComboBox.getSelectionModel().getSelectedItem();

            if (selectedService != null) {
                userdao userDAO = new userdao(); // Assuming UserDAO is your data access object
                int customerId = userDAO.getCustomerIdByUserId(user.getId());
                if (customerId != -1) {
                    customer customerDetails = userDAO.getCustomerById(customerId); // Fetch customer details

                    QuoteRequest quoteRequest = new QuoteRequest();
                    quoteRequest.setCustomerId(customerId);
                    quoteRequest.setServiceId(selectedService.getId());
                    quoteRequest.setServiceName(selectedService.getName());
                    quoteRequest.setCustomerName(customerDetails.getName());
                    quoteRequest.setCustomerAddress(customerDetails.getAddress());
                    quoteRequest.setCustomerPhone(customerDetails.getPhone());
                    quoteRequest.setStatus("Pending");

                    QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO(); // Assuming QuoteRequestDAO is your data
                                                                             // access object
                    quoteRequestDAO.addQuoteRequest(quoteRequest);
                    showAlert(Alert.AlertType.INFORMATION, "Quote Requested", "Quote requested successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Customer ID not found.");
                }
            }
        });
        VBox vbox = new VBox(10); // Spacing between elements
        vbox.setPadding(new Insets(10)); // Padding around the VBox
        vbox.getChildren().addAll(headerLabel, serviceComboBox, requestQuoteButton);

        quoteRequestPane.setCenter(vbox);

        quoteRequestTab.setContent(quoteRequestPane);
        return quoteRequestTab;
    }

    // APPOINMENT TAB-CUSTOMER

    private Tab createAppointmentsTab() {
        Tab appointmentsTab = new Tab("Appointments");
        appointmentsTab.setClosable(false);

        BorderPane appointmentsPane = new BorderPane();

        Label headerLabel = new Label("Your Appointments");
        TextArea appointmentTextArea = new TextArea();
        Button loadAppointmentsButton = new Button("Load Appointments");
        Button rescheduleButton = new Button("Reschedule Appointment");
        DatePicker newDatePicker = new DatePicker();
        Label rescheduleStatusLabel = new Label();

        appointmentTextArea.setEditable(false);
        newDatePicker.setPromptText("Select new date");

        loadAppointmentsButton.setOnAction(event -> {
            appointmentdao appointmentDAO = new appointmentdao(); // Assuming AppointmentDAO is your data access object
            userdao userDAO = new userdao(); // Assuming UserDAO is your data access object
            int customerId = userDAO.getCustomerIdByUserId(user.getId());
            int staffId = userDAO.getStaffIdByUserId(user.getId());
            List<appointment> appointments = appointmentDAO.getAppointmentsByCustomerId(customerId);
            StringBuilder sb = new StringBuilder();
            for (appointment appointment : appointments) {
                sb.append("Appointment ID: ").append(appointment.getId())
                        .append(" - Service ID: ").append(appointment.getServiceId())
                        .append(" - Date: ").append(appointment.getDate())
                        .append(" - Time: ").append(appointment.getTime())
                        .append(" - Status: ").append(appointment.getStatus())
                        .append(" - Staff: ").append(appointment.getStaffName())
                        .append(" - Phone: ").append(appointment.getStaffPhone())
                        .append("\n");
            }
            appointmentTextArea.setText(sb.toString());
        });

        rescheduleButton.setOnAction(event -> {
            appointmentdao appointmentDAO = new appointmentdao(); // Assuming AppointmentDAO is your data access object
            userdao userDAO = new userdao(); // Assuming UserDAO is your data access object
            int customerId = userDAO.getCustomerIdByUserId(user.getId());
            List<appointment> appointments = appointmentDAO.getAppointmentsByCustomerId(customerId);

            // Extract the first appointment for simplicity; in a real application, you
            // should let the user choose
            if (!appointments.isEmpty()) {
                appointment appointment = appointments.get(0);

                if (appointment.getRescheduleCount() < 5) {
                    LocalDate newDate = newDatePicker.getValue();
                    if (newDate != null) {
                        appointment.setDate(java.sql.Date.valueOf(newDate));
                        appointment.setRescheduleCount(appointment.getRescheduleCount() + 1);
                        appointmentDAO.updateAppointment(appointment); // Implement this method in your DAO
                        rescheduleStatusLabel.setText("Appointment rescheduled to " + newDate);
                    } else {
                        rescheduleStatusLabel.setText("Please select a new date.");
                    }
                } else {
                    rescheduleStatusLabel.setText("You have reached the maximum number of reschedules.");
                }
            } else {
                rescheduleStatusLabel.setText("No appointments found to reschedule.");
            }
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, appointmentTextArea, newDatePicker, rescheduleButton,
                rescheduleStatusLabel, loadAppointmentsButton);

        appointmentsPane.setCenter(vbox);

        appointmentsTab.setContent(appointmentsPane);
        return appointmentsTab;
    }

    // PENDING QUOTE REQUEST
    private Tab createPendingQuoteReqTab() {
        Tab pendingQuoteTab = new Tab("Pending Quote Requests");
        pendingQuoteTab.setClosable(false);

        BorderPane pendingQuotePane = new BorderPane();

        Label headerLabel = new Label("Pending Quote Requests");
        TextArea quoteRequestTextArea = new TextArea();
        Button loadQuoteRequestsButton = new Button("Load Pending Requests");

        quoteRequestTextArea.setEditable(false);

        loadQuoteRequestsButton.setOnAction(event -> {
            QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
            userdao userDAO = new userdao();
            int customerId = userDAO.getCustomerIdByUserId(user.getId());
            List<QuoteRequest> quoteRequests = quoteRequestDAO.getQuoteRequestsByCustomerId(customerId);
            StringBuilder sb = new StringBuilder();
            for (QuoteRequest quoteRequest : quoteRequests) {
                sb.append("Service: ").append(quoteRequest.getServiceId())
                        .append(" - Status: ").append(quoteRequest.getStatus())
                        .append("\n");
            }
            quoteRequestTextArea.setText(sb.toString());
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, quoteRequestTextArea, loadQuoteRequestsButton);

        pendingQuotePane.setCenter(vbox);

        pendingQuoteTab.setContent(pendingQuotePane);
        return pendingQuoteTab;
    }

    // Service history
    private Tab createCompletedQuoteReqTab() {
        Tab CompletedQuoteTab = new Tab("Service History");
        CompletedQuoteTab.setClosable(false);

        BorderPane CompletedQuotePane = new BorderPane();

        Label headerLabel = new Label("Service History");
        TextArea quoteRequestTextArea = new TextArea();
        Button loadQuoteRequestsButton = new Button("Load Service History");

        quoteRequestTextArea.setEditable(false);

        loadQuoteRequestsButton.setOnAction(event -> {
            servicehistorydao serviceHistoryDAO = new servicehistorydao();
            userdao userDAO = new userdao();
            int customerId = userDAO.getCustomerIdByUserId(user.getId());
            List<serviceHistory> serviceHistories = servicehistorydao.getServiceHistoryByCustomerId(customerId);
            StringBuilder sb = new StringBuilder();
            for (serviceHistory history : serviceHistories) {
                sb.append("Service: ").append(history.getServiceName())
                        .append(" - Date: ").append(history.getDate())
                        .append(" - Cost: ₹").append(history.getCost())
                        .append("\n");
            }
            quoteRequestTextArea.setText(sb.toString());
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, quoteRequestTextArea, loadQuoteRequestsButton);

        CompletedQuotePane.setCenter(vbox);

        CompletedQuoteTab.setContent(CompletedQuotePane);
        return CompletedQuoteTab;
    }
}
