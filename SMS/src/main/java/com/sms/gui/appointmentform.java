package main.java.com.sms.gui;

import main.java.com.sms.dao.appointmentdao;
import main.java.com.sms.model.appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class appointmentform extends JFrame {
    private JTextArea appointmentTextArea;
    private JButton loadAppointmentsButton;

    public appointmentform() {
        setTitle("Manage Appointments");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        appointmentTextArea = new JTextArea();
        appointmentTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(appointmentTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        loadAppointmentsButton = new JButton("Load Appointments");
        loadAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<appointment> appointments = appointmentdao.getAllAppointments();
                appointmentTextArea.setText("");
                for (appointment app : appointments) {
                    appointmentTextArea.append("ID: " + app.getId() + " - Service ID: " + app.getServiceId() + " - Date: " + app.getDate() + " - Assigned Employee ID: " + app.getAssignedEmployeeId() + "\n");
                }
            }
        });
        buttonPanel.add(loadAppointmentsButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
