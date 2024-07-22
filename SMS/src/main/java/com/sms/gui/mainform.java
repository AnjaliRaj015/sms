package main.java.com.sms.gui;

import main.java.com.sms.dao.servicedao;
import main.java.com.sms.dao.QuoteRequestDAO;
import main.java.com.sms.dao.appointmentdao;
import main.java.com.sms.dao.userdao;
import main.java.com.sms.model.service;
import main.java.com.sms.model.user;
import main.java.com.sms.model.QuoteRequest;
import main.java.com.sms.model.appointment;
import main.java.com.sms.model.customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class mainform extends JFrame {
    private user user;
    private JTabbedPane tabbedPane;

    public mainform(user user) {
        this.user = user;
        setTitle("Service Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        if (user.getRole().equals("staff")) {
            initializeStaffComponents();
        } else if (user.getRole().equals("customer")) {
            initializeCustomerComponents();
        }

        add(tabbedPane);
        setVisible(true);
    }

    private void initializeStaffComponents() {
        JPanel servicePanel = new JPanel(new BorderLayout());
        JPanel appointmentPanel = new JPanel(new BorderLayout());
        JPanel customerPanel = new JPanel(new BorderLayout());
        JPanel incomingRequestPanel = new JPanel(new BorderLayout());

        // Service Management
        servicePanel.add(new JLabel("Service Management"), BorderLayout.NORTH);
        JTextArea serviceTextArea = new JTextArea(20, 50);
        JButton loadServicesButton = new JButton("Load Services");
        JButton addServiceButton = new JButton("Add New Service");
        List<service> services = servicedao.getAllServices();
        serviceTextArea.setText("");
        for (service service : services) {
            serviceTextArea.append(service.getName() + " - " + service.getDescription() + " - " + service.getCost() + "\n");
        }
        loadServicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<service> services = servicedao.getAllServices();
                serviceTextArea.setText("");
                for (service service : services) {
                    serviceTextArea.append(service.getName() + " - " + service.getDescription() + " - " + service.getCost() + "\n");
                }
            }
        });
        addServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new serviceform(); // Show form to add new service
            }
        });
        servicePanel.add(new JScrollPane(serviceTextArea), BorderLayout.CENTER);
        JPanel serviceButtonsPanel = new JPanel();
        serviceButtonsPanel.add(loadServicesButton);
        serviceButtonsPanel.add(addServiceButton);
        servicePanel.add(serviceButtonsPanel, BorderLayout.SOUTH);

// Appointment Management
appointmentPanel.add(new JLabel("Appointment Management"), BorderLayout.NORTH);
JTextArea appointmentTextArea = new JTextArea(20, 50);
JButton loadAppointmentsButton = new JButton("Load Appointments");

loadAppointmentsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        appointmentdao appointmentDAO = new appointmentdao();
        List<appointment> appointments = appointmentDAO.getAppointmentsByStaffId(user.getId()); // Assuming `getAppointmentsByStaffId` method exists
        appointmentTextArea.setText("");
        for (appointment appointment : appointments) {
            appointmentTextArea.append("Service ID: " + appointment.getServiceId() + 
                                       " - Customer ID: " + appointment.getCustomerId() + 
                                       " - Date: " + appointment.getDate() + 
                                       " - Time: " + appointment.getTime() + "\n");
        }
    }
});
appointmentPanel.add(new JScrollPane(appointmentTextArea), BorderLayout.CENTER);
JPanel appointmentButtonsPanel = new JPanel();
appointmentButtonsPanel.add(loadAppointmentsButton);
appointmentPanel.add(appointmentButtonsPanel, BorderLayout.SOUTH);

    // Customer Management
    customerPanel.add(new JLabel("Customer Management"), BorderLayout.NORTH);
    JTextArea customerTextArea = new JTextArea(20, 50);
    JButton addCustomerButton = new JButton("Add New Customer");
    JButton manageCustomerButton = new JButton("Manage Existing Customer");
    List<customer> customers = userdao.getCustomers(); // Assuming customerdao is a DAO class
    customerTextArea.setText("");
    for (customer c : customers) {
        customerTextArea.append(c.getUsername() + " - " +  " - " + c.getEmail() + "\n");
    }
    addCustomerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new customerform(); // Show form to add new customer
        }
    });
    manageCustomerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ManageExistingCustomerForm(); // Show form to manage existing customers
        }
    });
    customerPanel.add(new JScrollPane(customerTextArea), BorderLayout.CENTER);
    JPanel customerButtonsPanel = new JPanel();
    customerButtonsPanel.add(addCustomerButton);
    customerButtonsPanel.add(manageCustomerButton);
    customerPanel.add(customerButtonsPanel, BorderLayout.SOUTH);

    // Incoming Requests Panel
    incomingRequestPanel.add(new JLabel("Incoming Requests"), BorderLayout.NORTH);
    
    JTextArea incomingRequestTextArea = new JTextArea(20, 50);
    JButton loadIncomingRequestsButton = new JButton("Load Incoming Requests");
    JButton takeRequestButton = new JButton("Take Request");

    loadIncomingRequestsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
            List<QuoteRequest> quoteRequests = quoteRequestDAO.getPendingQuoteRequests();
            incomingRequestTextArea.setText("");
            for (QuoteRequest quoteRequest : quoteRequests) {
                incomingRequestTextArea.append(
                    "ID: " + quoteRequest.getId() +
                    " | Customer: " + quoteRequest.getCustomerName() +
                    " | Service: " + quoteRequest.getServiceName() +
                    " | Address: " + quoteRequest.getCustomerAddress() +
                    " | Phone: " + quoteRequest.getCustomerPhone() + "\n"
                );
            }
        }
    });

    takeRequestButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Display a dialog to choose the request ID and appointment date
            JTextField requestIdField = new JTextField();
            JTextField dateField = new JTextField();
            Object[] message = {
                "Request ID:", requestIdField,
                "Appointment Date (yyyy-mm-dd):", dateField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Take Request", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int requestId = Integer.parseInt(requestIdField.getText());
                String dateStr = dateField.getText();
                java.sql.Date appointmentDate = java.sql.Date.valueOf(dateStr); // Assuming proper date format

                QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
                List<QuoteRequest> quoteRequests = quoteRequestDAO.getPendingQuoteRequests();
                QuoteRequest selectedRequest = null;

                for (QuoteRequest qr : quoteRequests) {
                    if (qr.getId() == requestId) {
                        selectedRequest = qr;
                        break;
                    }
                }

                if (selectedRequest != null) {
                    // Create and add the new appointment
                    appointment newAppointment = new appointment();
                    newAppointment.setServiceId(selectedRequest.getServiceId());
                    newAppointment.setCustomerId(selectedRequest.getCustomerId());
                    newAppointment.setStaffId(user.getId()); // Assuming `user` is the staff taking the request
                    newAppointment.setDate(appointmentDate);
                    newAppointment.setTime("09:00:00"); // Set a default time or extend UI for time input
                    newAppointment.setStatus("scheduled");

                    appointmentdao appointmentDAO = new appointmentdao();
                    appointmentdao.addAppointment(newAppointment);

                    // Update the request status to "accepted"
                    selectedRequest.setStatus("accepted");
                    quoteRequestDAO.updateQuoteRequest(selectedRequest);

                    JOptionPane.showMessageDialog(mainform.this, "Request accepted and appointment scheduled!");

                    // Optionally update the staff's appointment dashboard (if applicable)
                    // Add code to refresh staff's appointment dashboard
                } else {
                    JOptionPane.showMessageDialog(mainform.this, "Invalid Request ID!");
                }
            }
        }
    });

    incomingRequestPanel.add(new JScrollPane(incomingRequestTextArea), BorderLayout.CENTER);
    JPanel incomingRequestButtonsPanel = new JPanel();
    incomingRequestButtonsPanel.add(loadIncomingRequestsButton);
    incomingRequestButtonsPanel.add(takeRequestButton);
    incomingRequestPanel.add(incomingRequestButtonsPanel, BorderLayout.SOUTH);

    tabbedPane.addTab("Services", servicePanel);
    tabbedPane.addTab("Appointments", appointmentPanel);
    tabbedPane.addTab("Customers", customerPanel);
    tabbedPane.addTab("Incoming Requests", incomingRequestPanel);
    }

    private void initializeCustomerComponents() {
        JPanel servicePanel = new JPanel(new BorderLayout());
        JPanel appointmentPanel = new JPanel(new BorderLayout());
        JPanel quoteRequestPanel = new JPanel(new BorderLayout());

        // Browse Services
        servicePanel.add(new JLabel("Available Services"), BorderLayout.NORTH);
        JTextArea serviceTextArea = new JTextArea(20, 50);
        JButton loadServicesButton = new JButton("Load Services");
        loadServicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<service> services = servicedao.getAllServices();
                serviceTextArea.setText("");
                for (service service : services) {
                    serviceTextArea.append(service.getName() + " - " + service.getDescription() + " - " + service.getCost() + "\n");
                }
            }
        });
        servicePanel.add(new JScrollPane(serviceTextArea), BorderLayout.CENTER);
        servicePanel.add(loadServicesButton, BorderLayout.SOUTH);

        // Request a Quote
        JPanel quotePanel = new JPanel(new BorderLayout());
        quotePanel.add(new JLabel("Request a Quote"), BorderLayout.NORTH);
        JComboBox<service> serviceComboBox = new JComboBox<>();
        List<service> services = servicedao.getAllServices();
        for (service service : services) {
            serviceComboBox.addItem(service);
        }
        JButton requestQuoteButton = new JButton("Request Quote");
        requestQuoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service selectedService = (service) serviceComboBox.getSelectedItem();
                if (selectedService != null) {
                    QuoteRequest quoteRequest = new QuoteRequest();
                    quoteRequest.setCustomerId(user.getId());
                    quoteRequest.setServiceId(selectedService.getId());
                    quoteRequest.setStatus("Pending");
                    new QuoteRequestDAO().addQuoteRequest(quoteRequest);
                    JOptionPane.showMessageDialog(mainform.this, "Quote requested successfully!");
                }
            }
        });
        quotePanel.add(serviceComboBox, BorderLayout.CENTER);
        quotePanel.add(requestQuoteButton, BorderLayout.SOUTH);

// View Appointments
appointmentPanel.add(new JLabel("Your Appointments"), BorderLayout.NORTH);
JTextArea appointmentTextArea = new JTextArea(20, 50);
JButton loadAppointmentsButton = new JButton("Load Appointments");

loadAppointmentsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        appointmentdao appointmentDAO = new appointmentdao();
        List<appointment> appointments = appointmentDAO.getAppointmentsByCustomerId(user.getId());
        appointmentTextArea.setText("");
        for (appointment appointment : appointments) {
            appointmentTextArea.append("Service ID: " + appointment.getServiceId() + 
                                       " - Date: " + appointment.getDate() + 
                                       " - Time: " + appointment.getTime() + 
                                       " - Status: " + "scheduled" + 
                                       " - Staff: " + appointment.getStaffName() + 
                                       " - Phone: " + appointment.getStaffPhone() + "\n");
        }
    }
});

appointmentPanel.add(new JScrollPane(appointmentTextArea), BorderLayout.CENTER);
appointmentPanel.add(loadAppointmentsButton, BorderLayout.SOUTH);
        // Pending Quote Requests
        quoteRequestPanel.add(new JLabel("Pending Quote Requests"), BorderLayout.NORTH);
        JTextArea quoteRequestTextArea = new JTextArea(20, 50);
        JButton loadQuoteRequestsButton = new JButton("Load Pending Requests");
        loadQuoteRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
                List<QuoteRequest> quoteRequests = quoteRequestDAO.getQuoteRequestsByCustomerId(user.getId());
                quoteRequestTextArea.setText("");
                for (QuoteRequest quoteRequest : quoteRequests) {
                    quoteRequestTextArea.append("Service: " + quoteRequest.getServiceId() + 
                                                " - Status: " + quoteRequest.getStatus() + "\n");
                }
            }
        });
        quoteRequestPanel.add(new JScrollPane(quoteRequestTextArea), BorderLayout.CENTER);
        quoteRequestPanel.add(loadQuoteRequestsButton, BorderLayout.SOUTH);
    
        tabbedPane.addTab("Browse Services", servicePanel);
        tabbedPane.addTab("Request a Quote", quotePanel);
        tabbedPane.addTab("Your Appointments", appointmentPanel);
        tabbedPane.addTab("Pending Requests", quoteRequestPanel);

    }

    private customer getSelectedCustomer() {
        // Implement this method to return the selected customer from the customer list
        // For now, return null to indicate this method needs implementation
        return null;
    }
}