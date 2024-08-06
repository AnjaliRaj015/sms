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

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

        // Create buttons for the tabs
        Button customerdashButton = new Button("Home");
        Button servicesButton = new Button("Services");
        Button quoteRequestsButton = new Button("Quote Requests");
        Button appointmentsButton = new Button("Appointments");
        Button pendingQuoteReqButton = new Button("Pending Quote Requests");
        Button completedQuoteReqButton = new Button("Completed Quote Requests");

        Button homeButton = new Button("Home");
        Button customerButton = new Button("Customer Management");
        Button incomButton = new Button("Incoming Request Button");

        if (user.getRole().equals("customer")) {

            // Create a VBox for the buttons (acting as a vertical menu)
            VBox menu = new VBox(10, customerdashButton, servicesButton, quoteRequestsButton, appointmentsButton, pendingQuoteReqButton,
                    completedQuoteReqButton);
            menu.setPadding(new Insets(10));
            menu.setMinWidth(200);

            // Create a BorderPane for the content on the right
            BorderPane contentPane = new BorderPane();

            // Create the main layout with menu on the left and content on the right
            BorderPane mainLayout = new BorderPane();
            mainLayout.setLeft(menu);
            mainLayout.setCenter(contentPane);

            // Set the default content
            contentPane.setCenter(createProfileTab().getContent());

            // Add event handlers for the buttons
            customerdashButton.setOnAction(event->contentPane.setCenter(createProfileTab().getContent()));
            servicesButton.setOnAction(event -> contentPane.setCenter(createServiceTab().getContent()));
            quoteRequestsButton.setOnAction(event -> contentPane.setCenter(createQuoteRequestTab().getContent()));
            appointmentsButton.setOnAction(event -> contentPane.setCenter(createAppointmentsTab().getContent()));
            pendingQuoteReqButton.setOnAction(event -> contentPane.setCenter(createPendingQuoteReqTab().getContent()));
            completedQuoteReqButton
                    .setOnAction(event -> contentPane.setCenter(createCompletedQuoteReqTab().getContent()));

            // Adding logout button and setting its action
            Button logoutButton = new Button("Logout");
            logoutButton.setOnAction(event -> {
                Main.showMainWindow(); // Redirect to the main application window
                primaryStage.close();
            });

            VBox logoutBox = new VBox(logoutButton);
            logoutBox.setPadding(new Insets(10));
            logoutBox.setSpacing(8);

            contentPane.setBottom(logoutBox);

            String css = getClass().getResource("styles.css").toExternalForm();

            Scene scene = new Scene(mainLayout, 1000, 800);
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else if (user.getRole().equals("staff")) {

            // Create a VBox for the buttons (acting as a vertical menu)
            VBox menu = new VBox(10, homeButton, servicesButton, appointmentsButton, customerButton, incomButton);
            menu.setPadding(new Insets(10));
            menu.setMinWidth(200);

            // Create a BorderPane for the content on the right
            BorderPane contentPane = new BorderPane();

            // Create the main layout with menu on the left and content on the right
            BorderPane mainLayout = new BorderPane();
            mainLayout.setLeft(menu);
            mainLayout.setCenter(contentPane);

            // Set the default content
            contentPane.setCenter(createHomeTab().getContent());

            // Add event handlers for the buttons
            homeButton.setOnAction(event -> contentPane.setCenter(createHomeTab().getContent()));
            servicesButton.setOnAction(event -> contentPane.setCenter(createServiceManagementTab().getContent()));
            appointmentsButton
                    .setOnAction(event -> contentPane.setCenter(createAppointmentManagementTab().getContent()));
            customerButton.setOnAction(event -> contentPane.setCenter(createCustomerManagementTab().getContent()));
            incomButton.setOnAction(event -> contentPane.setCenter(createIncomingRequestsTab().getContent()));

            // Adding logout button and setting its action
            Button logoutButton = new Button("Logout");
            logoutButton.setOnAction(event -> {
                Main.showMainWindow(); // Redirect to the main application window
                primaryStage.close();
            });

            VBox logoutBox = new VBox(logoutButton);
            logoutBox.setPadding(new Insets(10));
            logoutBox.setSpacing(8);

            contentPane.setBottom(logoutBox);

            String css = getClass().getResource("styles.css").toExternalForm();

            Scene scene = new Scene(mainLayout, 800, 600);
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
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
        // Set column widths
        serviceColumn.setPrefWidth(200); // Width for Service Type column
        revenueColumn.setPrefWidth(200); // Width for Total Revenue column
        serviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        revenueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        tableView.getColumns().addAll(serviceColumn, revenueColumn);
        tableView.setPrefSize(600, 400); // Adjust the width and height as needed

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
        // Set column widths
        staffColumn.setPrefWidth(200); // Width for Staff Member column
        workloadColumn.setPrefWidth(200); // Width for Workload column

        staffColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        workloadColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        tableView.getColumns().addAll(staffColumn, workloadColumn);
        tableView.setPrefSize(600, 400); // Adjust the width and height as needed

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
        TableView<service> serviceTable = new TableView<>();
        Button loadServicesButton = new Button("Load Services");
        Button addServiceButton = new Button("Add New Service");

        // Apply CSS class
        loadServicesButton.getStyleClass().add("custom-button");
        addServiceButton.getStyleClass().add("custom-button");

        // Create columns
        TableColumn<service, String> nameColumn = new TableColumn<>("Service Name");
        TableColumn<service, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<service, Double> costColumn = new TableColumn<>("Cost");

        // Set column cell value factories
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        descriptionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        costColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCost()));

        // Set column widths
        nameColumn.setPrefWidth(200);
        descriptionColumn.setPrefWidth(300);
        costColumn.setPrefWidth(100);

        // Add columns to the table
        serviceTable.getColumns().addAll(nameColumn, descriptionColumn, costColumn);

        // Add Event Handlers
        loadServicesButton.setOnAction(e -> {
            List<service> services = servicedao.getAllServices();
            serviceTable.setItems(FXCollections.observableArrayList(services));
        });

        addServiceButton.setOnAction(e -> new serviceform()); // Show form to add new service

        // Layout setup for buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER_LEFT); // Align buttons to the left
        buttonBox.getChildren().addAll(loadServicesButton, addServiceButton);

        // Add components to the pane
        servicePane.setTop(serviceLabel);
        servicePane.setCenter(new ScrollPane(serviceTable)); // Use TableView inside ScrollPane
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
        TableView<appointment> appointmentTable = new TableView<>();
        Button loadAppointmentsButton = new Button("Load Appointments");
        Button markCompletedButton = new Button("Mark as Completed");

        // Apply CSS class
        loadAppointmentsButton.getStyleClass().add("custom-button");
        markCompletedButton.getStyleClass().add("custom-button");

        // Create columns
        TableColumn<appointment, String> serviceIdColumn = new TableColumn<>("Service ID");
        TableColumn<appointment, String> customerIdColumn = new TableColumn<>("Customer ID");
        TableColumn<appointment, String> dateColumn = new TableColumn<>("Date");
        TableColumn<appointment, String> timeColumn = new TableColumn<>("Time");

        // Set column cell value factories
        serviceIdColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getServiceId())));
        customerIdColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCustomerId())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));

        // Set column widths
        serviceIdColumn.setPrefWidth(120);
        customerIdColumn.setPrefWidth(120);
        dateColumn.setPrefWidth(150);
        timeColumn.setPrefWidth(100);

        // Add columns to the table
        appointmentTable.getColumns().addAll(serviceIdColumn, customerIdColumn, dateColumn, timeColumn);

        loadAppointmentsButton.setOnAction(e -> {
            appointmentdao appointmentDAO = new appointmentdao();
            userdao userDAO = new userdao();
            int staffId = userDAO.getStaffIdByUserId(user.getId());

            List<appointment> appointments = appointmentDAO.getAppointmentsByStaffId(staffId);
            appointmentTable.setItems(FXCollections.observableArrayList(appointments));
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

        // Layout setup for buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER_LEFT); // Align buttons to the left
        buttonBox.getChildren().addAll(loadAppointmentsButton, markCompletedButton);

        // Layout setup for the pane
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, appointmentTable, buttonBox);

        appointmentPane.setCenter(vbox);
        appointmentTab.setContent(appointmentPane);
        return appointmentTab;
    }

    // CUSTOMER MANAGEMENT TAB-STAFF
    private Tab createCustomerManagementTab() {
        Tab customerTab = new Tab("Customer Management");
        customerTab.setClosable(false);

        BorderPane customerPane = new BorderPane();

        // Create UI elements
        Label headerLabel = new Label("Customer Management");
        TableView<customer> customerTable = new TableView<>();
        Button loadCustomersButton = new Button("Load Customers");
        Button addCustomerButton = new Button("Add New Customer");
        Button manageCustomerButton = new Button("Manage Existing Customer");

        // Apply CSS class
        loadCustomersButton.getStyleClass().add("custom-button");
        addCustomerButton.getStyleClass().add("custom-button");
        manageCustomerButton.getStyleClass().add("custom-button");

        // Create columns
        TableColumn<customer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<customer, String> emailColumn = new TableColumn<>("Email");

        // Set column cell value factories
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        // Set column widths
        nameColumn.setPrefWidth(200);
        emailColumn.setPrefWidth(300);

        // Add columns to the table
        customerTable.getColumns().addAll(nameColumn, emailColumn);

        // Add Event Handlers
        loadCustomersButton.setOnAction(e -> {
            List<customer> customers = userdao.getCustomers(); // Assuming userdao is used for customer data
            customerTable.setItems(FXCollections.observableArrayList(customers));
        });

        addCustomerButton.setOnAction(e -> {
            new customerform().start(new Stage()); // Show form to add new customer
        });

        manageCustomerButton.setOnAction(e -> {
            new ManageExistingCustomerForm().start(new Stage()); // Show form to manage existing customers
        });

        // Layout setup for buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER_LEFT); // Align buttons to the left
        buttonBox.getChildren().addAll(loadCustomersButton, addCustomerButton, manageCustomerButton);

        // Layout setup for the pane
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, customerTable, buttonBox);

        customerPane.setCenter(vbox);
        customerTab.setContent(customerPane);
        return customerTab;
    }
    

    // INCOMING REQUEST MANAGEMENT TAB
    private Tab createIncomingRequestsTab() {
        Tab incomingRequestsTab = new Tab("Incoming Requests");
        incomingRequestsTab.setClosable(false);

        BorderPane incomingRequestsPane = new BorderPane();

        Label headerLabel = new Label("Incoming Requests");
        TableView<QuoteRequest> requestsTable = new TableView<>();
        Button loadIncomingRequestsButton = new Button("Load Incoming Requests");
        Button takeRequestButton = new Button("Take Request");
        Button rejectRequestButton = new Button("Reject Request");
        Button assignRequestButton = new Button("Assign Request");

        // Apply CSS class
        loadIncomingRequestsButton.getStyleClass().add("custom-button");
        takeRequestButton.getStyleClass().add("custom-button");
        rejectRequestButton.getStyleClass().add("custom-button");
        assignRequestButton.getStyleClass().add("custom-button");

        // Create columns
        TableColumn<QuoteRequest, String> idColumn = new TableColumn<>("ID");
        TableColumn<QuoteRequest, String> customerNameColumn = new TableColumn<>("Customer");
        TableColumn<QuoteRequest, String> serviceNameColumn = new TableColumn<>("Service");
        TableColumn<QuoteRequest, String> addressColumn = new TableColumn<>("Address");
        TableColumn<QuoteRequest, String> phoneColumn = new TableColumn<>("Phone");

        // Set column cell value factories
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        customerNameColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        serviceNameColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        addressColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerAddress()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerPhone()));

        // Set column widths
        idColumn.setPrefWidth(80);
        customerNameColumn.setPrefWidth(150);
        serviceNameColumn.setPrefWidth(150);
        addressColumn.setPrefWidth(200);
        phoneColumn.setPrefWidth(120);

        // Add columns to the table
        requestsTable.getColumns().addAll(idColumn, customerNameColumn, serviceNameColumn, addressColumn, phoneColumn);

        loadIncomingRequestsButton.setOnAction(e -> {
            QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
            List<QuoteRequest> quoteRequests = quoteRequestDAO.getPendingQuoteRequests();
            requestsTable.setItems(FXCollections.observableArrayList(quoteRequests));
        });

        takeRequestButton.setOnAction(e -> handleRequest("accepted"));
        rejectRequestButton.setOnAction(e -> handleRequest("rejected"));
        assignRequestButton.setOnAction(e -> assignRequestToEmployee());

        // Layout setup for buttons
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER_LEFT); // Align buttons to the left
        buttonBox.getChildren().addAll(loadIncomingRequestsButton, takeRequestButton, rejectRequestButton,
                assignRequestButton);

        // Layout setup for the pane
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, requestsTable, buttonBox);

        incomingRequestsPane.setCenter(vbox);
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
    private Tab createProfileTab() {
        Tab profileTab = new Tab("Profile");
        profileTab.setClosable(false);
    
        BorderPane profilePane = new BorderPane();
    
        Label headerLabel = new Label("Your Profile");
        Label usernameLabel = new Label();
        Label passwordLabel = new Label();
        Label nameLabel = new Label();
        Label emailLabel = new Label();
        Label phoneLabel = new Label();
        Label addressLabel = new Label();
        Button editButton = new Button("Edit Profile");
    
        // Apply CSS class
        editButton.getStyleClass().add("custom-button");
    
        // Load user details
        loadUserDetails(usernameLabel, passwordLabel, nameLabel, emailLabel, phoneLabel, addressLabel);
    
        editButton.setOnAction(event -> {
            openEditProfileForm();
        });
    
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, usernameLabel, passwordLabel, nameLabel, emailLabel, phoneLabel, addressLabel, editButton);
    
        profilePane.setCenter(vbox);
    
        profileTab.setContent(profilePane);
        return profileTab;
    }
    
    private void loadUserDetails(Label usernameLabel, Label passwordLabel, Label nameLabel, Label emailLabel, Label phoneLabel, Label addressLabel) {
        // Fetch user details from the database
        userdao userDAO = new userdao();
        int customerId = userDAO.getCustomerIdByUserId(user.getId());
        user currentUser = userDAO.getCustomerById(customerId);
    
        usernameLabel.setText("Username: " + currentUser.getUsername());
        passwordLabel.setText("Password: " + currentUser.getPassword());
        nameLabel.setText("Name: " + currentUser.getName());
        emailLabel.setText("Email: " + currentUser.getEmail());
        phoneLabel.setText("Phone: " + currentUser.getPhone());
        addressLabel.setText("Address: " + currentUser.getAddress());
    }
    private void openEditProfileForm() {
        Stage editProfileStage = new Stage();
        editProfileStage.setTitle("Edit Profile");
    
        VBox editForm = new VBox(10);
        editForm.setPadding(new Insets(10));
        editForm.setStyle("-fx-background-color: #d1d1e9;"); // Background color
    
        TextField usernameField = new TextField();
        TextField passwordField = new TextField();
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        TextField addressField = new TextField();
        Button saveButton = new Button("Save");
    
        // Set button style
        saveButton.setStyle("-fx-background-color: #4749c4; -fx-text-fill: white; -fx-font-size: 16px;");
    
        // Load current user details into fields
        userdao userDAO = new userdao();
        int customerId = userDAO.getCustomerIdByUserId(user.getId());
        user currentUser = userDAO.getCustomerById(customerId);
    
        usernameField.setText(currentUser.getUsername());
        passwordField.setText(currentUser.getPassword());
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
        phoneField.setText(currentUser.getPhone());
        addressField.setText(currentUser.getAddress());
    
        saveButton.setOnAction(event -> {
            // Update user details
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
    
            userDAO.updateUser(user.getId(), username, password, name, email, phone, address);
            userDAO.updateCustomer(customerId, username, password, name, email, phone, address);
            editProfileStage.close();
            // Refresh profile tab after saving
        });
    
        editForm.getChildren().addAll(
            new Label("Username:"), usernameField,
            new Label("Password:"), passwordField,
            new Label("Name:"), nameField,
            new Label("Email:"), emailField,
            new Label("Phone:"), phoneField,
            new Label("Address:"), addressField,
            saveButton
        );
    
        Scene scene = new Scene(editForm, 400, 500);
        editProfileStage.setScene(scene);
        editProfileStage.show();
    }
    
    

        

    // BROWSE SERVICES-CUSTOMER TAB
    private Tab createServiceTab() {
        Tab serviceTab = new Tab("Services");
        serviceTab.setClosable(false);
    
        // Create the main layout for the tab
        BorderPane servicePane = new BorderPane();
    
        // Create the UI components
        Label headerLabel = new Label("Available Services");
        TableView<service> serviceTable = new TableView<>();
        Button loadServicesButton = new Button("Load Services");

        // Apply CSS class
        loadServicesButton.getStyleClass().add("custom-button");

        // Create columns for the TableView
        TableColumn<service, String> nameColumn = new TableColumn<>("Name");
        TableColumn<service, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<service, String> costColumn = new TableColumn<>("Cost");
    
        // Set cell value factories
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        costColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCost())));
    
        // Set column widths
        nameColumn.setPrefWidth(150);
        descriptionColumn.setPrefWidth(250);
        costColumn.setPrefWidth(100);
    
        // Add columns to the TableView
        serviceTable.getColumns().addAll(nameColumn, descriptionColumn, costColumn);
    
        // Set up the button action
        loadServicesButton.setOnAction(event -> {
            servicedao serviceDAO = new servicedao(); // Assuming ServiceDAO is your data access object
            List<service> services = serviceDAO.getAllServices();
            serviceTable.setItems(FXCollections.observableArrayList(services));
        });

        // Set up the layout
        VBox vbox = new VBox(10); // Spacing between elements
        vbox.setPadding(new Insets(10)); // Padding around the VBox
        vbox.getChildren().addAll(headerLabel, serviceTable, loadServicesButton);

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
        
        // Apply CSS class
        requestQuoteButton.getStyleClass().add("custom-button");

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

        // Create the UI components
        Label headerLabel = new Label("Your Appointments");
        TableView<appointment> appointmentTable = new TableView<>();
        Button loadAppointmentsButton = new Button("Load Appointments");
        Button rescheduleButton = new Button("Reschedule Appointment");
        DatePicker newDatePicker = new DatePicker();
        Label rescheduleStatusLabel = new Label();

        // Apply CSS class
        loadAppointmentsButton.getStyleClass().add("custom-button");
        rescheduleButton.getStyleClass().add("custom-button");

        // Create columns for the TableView
        TableColumn<appointment, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<appointment, Integer> serviceIdColumn = new TableColumn<>("Service ID");
        TableColumn<appointment, String> dateColumn = new TableColumn<>("Date");
        TableColumn<appointment, String> timeColumn = new TableColumn<>("Time");
        TableColumn<appointment, String> statusColumn = new TableColumn<>("Status");
        TableColumn<appointment, String> staffNameColumn = new TableColumn<>("Staff");
        TableColumn<appointment, String> staffPhoneColumn = new TableColumn<>("Phone");

        // Set cell value factories
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        serviceIdColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getServiceId()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        staffNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStaffName()));
        staffPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStaffPhone()));

        // Set column widths
        idColumn.setPrefWidth(50);
        serviceIdColumn.setPrefWidth(100);
        dateColumn.setPrefWidth(100);
        timeColumn.setPrefWidth(80);
        statusColumn.setPrefWidth(100);
        staffNameColumn.setPrefWidth(150);
        staffPhoneColumn.setPrefWidth(100);

        // Add columns to the TableView
        appointmentTable.getColumns().addAll(idColumn, serviceIdColumn, dateColumn, timeColumn, statusColumn,
                staffNameColumn, staffPhoneColumn);

        loadAppointmentsButton.setOnAction(event -> {
            appointmentdao appointmentDAO = new appointmentdao(); // Assuming AppointmentDAO is your data access object
            userdao userDAO = new userdao(); // Assuming UserDAO is your data access object
            int customerId = userDAO.getCustomerIdByUserId(user.getId());
            int staffId = userDAO.getStaffIdByUserId(user.getId());
            List<appointment> appointments = appointmentDAO.getAppointmentsByCustomerId(customerId);
            appointmentTable.setItems(FXCollections.observableArrayList(appointments));

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
        vbox.getChildren().addAll(headerLabel, appointmentTable, newDatePicker, rescheduleButton,
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
        TableView<QuoteRequest> quoteRequestTable = new TableView<>();
        Button loadQuoteRequestsButton = new Button("Load Pending Requests");

        // Apply CSS class
        loadQuoteRequestsButton.getStyleClass().add("custom-button");

        // Create columns for the TableView
        TableColumn<QuoteRequest, Integer> serviceIdColumn = new TableColumn<>("Service ID");
        TableColumn<QuoteRequest, String> statusColumn = new TableColumn<>("Status");

        // Set cell value factories
        serviceIdColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getServiceId()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        // Set column widths
        serviceIdColumn.setPrefWidth(150);
        statusColumn.setPrefWidth(200);

        // Add columns to the TableView
        quoteRequestTable.getColumns().addAll(serviceIdColumn, statusColumn);

        loadQuoteRequestsButton.setOnAction(event -> {
            QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
            userdao userDAO = new userdao();
            int customerId = userDAO.getCustomerIdByUserId(user.getId());
            List<QuoteRequest> quoteRequests = quoteRequestDAO.getQuoteRequestsByCustomerId(customerId);
            quoteRequestTable.setItems(FXCollections.observableArrayList(quoteRequests));
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, quoteRequestTable, loadQuoteRequestsButton);

        pendingQuotePane.setCenter(vbox);

        pendingQuoteTab.setContent(pendingQuotePane);
        return pendingQuoteTab;
    }

    // Service history
    private Tab createCompletedQuoteReqTab() {
        Tab completedQuoteTab = new Tab("Service History");
        completedQuoteTab.setClosable(false);

        BorderPane completedQuotePane = new BorderPane();

        Label headerLabel = new Label("Service History");
        TableView<serviceHistory> serviceHistoryTable = new TableView<>();
        Button loadServiceHistoryButton = new Button("Load Service History");

        // Apply CSS class
        loadServiceHistoryButton.getStyleClass().add("custom-button");

        // Create columns for the TableView
        TableColumn<serviceHistory, String> serviceNameColumn = new TableColumn<>("Service Name");
        TableColumn<serviceHistory, String> dateColumn = new TableColumn<>("Date");
        TableColumn<serviceHistory, Double> costColumn = new TableColumn<>("Cost");

        // Set cell value factories
        serviceNameColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        costColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCost()).asObject());

        // Set column widths
        serviceNameColumn.setPrefWidth(200);
        dateColumn.setPrefWidth(150);
        costColumn.setPrefWidth(100);

        // Add columns to the TableView
        serviceHistoryTable.getColumns().addAll(serviceNameColumn, dateColumn, costColumn);

        loadServiceHistoryButton.setOnAction(event -> {
            servicehistorydao serviceHistoryDAO = new servicehistorydao();
            userdao userDAO = new userdao();
            int customerId = userDAO.getCustomerIdByUserId(user.getId());
            List<serviceHistory> serviceHistories = servicehistorydao.getServiceHistoryByCustomerId(customerId);
            serviceHistoryTable.setItems(FXCollections.observableArrayList(serviceHistories));
        });

        // Set up the layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(headerLabel, serviceHistoryTable, loadServiceHistoryButton);

        completedQuotePane.setCenter(vbox);

        completedQuoteTab.setContent(completedQuotePane);
        return completedQuoteTab;
    }
}
