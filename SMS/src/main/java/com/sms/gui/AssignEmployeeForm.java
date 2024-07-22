package main.java.com.sms.gui;

import main.java.com.sms.dao.appointmentdao;
import main.java.com.sms.dao.userdao;
import main.java.com.sms.model.appointment;
import main.java.com.sms.model.staff;
import main.java.com.sms.model.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignEmployeeForm extends JFrame {
    private JComboBox<appointment> appointmentComboBox;
    private JComboBox<user> employeeComboBox;

    public AssignEmployeeForm() {
        setTitle("Assign Employee to Appointment");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Select Appointment:"));
        appointmentComboBox = new JComboBox<>();
        List<appointment> appointments = appointmentdao.getAllAppointments();
        for (appointment app : appointments) {
            appointmentComboBox.addItem(app);
        }
        add(appointmentComboBox);

        add(new JLabel("Select Employee:"));
        employeeComboBox = new JComboBox<>();
        List<staff> employees = userdao.getEmployees();
        for (user emp : employees) {
            employeeComboBox.addItem(emp);
        }
        add(employeeComboBox);

        JButton assignButton = new JButton("Assign");
        add(assignButton);
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appointment selectedAppointment = (appointment) appointmentComboBox.getSelectedItem();
                user selectedEmployee = (user) employeeComboBox.getSelectedItem();

                if (selectedAppointment != null && selectedEmployee != null) {
                    selectedAppointment.setAssignedEmployeeId(selectedEmployee.getId());
                    appointmentdao.updateAppointment(selectedAppointment);
                    JOptionPane.showMessageDialog(AssignEmployeeForm.this, "Employee assigned successfully!");
                    dispose();
                }
            }
        });

        setVisible(true);
    }
}
