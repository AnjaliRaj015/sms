USE sms;

INSERT INTO users (full_name, username, password, role, email, phone,address) VALUES
('John Doe','john_doe', 'customer', 'customer', 'john@example.com', '123-456-7890','Elm Street'),
('Jane Doe','jane_doe', 'admin', 'staff', 'jane@example.com', '987-654-3210','Elm Street');
-- Insert sample data into customers
INSERT INTO customers (user_id, username, password, full_name, email,address) VALUES (1, 'john_doe', 'password123', 'John Doe', 'john@example.com','Elm Street');
INSERT INTO staffs (user_id, username, password, full_name, email,address) VALUES (2, 'jane_doe', 'password123', 'Jane Doe', 'jane@example.com','Elm Street');


INSERT INTO services (name, description, estimated_duration, cost) VALUES
('Plumbing', 'Fixing leaks and installing pipes', 60, 50.0),
('Electrical Repairs', 'Fixing electrical issues', 90, 70.0),
('Cleaning', 'House cleaning services', 120, 100.0);
INSERT INTO appointments (customer_id, service_id, staff_id, date,time, status) VALUES
(1, 1, 2, '2024-07-25','10:00:00', 'scheduled'),
(2, 2, 2, '2024-07-26','14:00:00', 'scheduled');
INSERT INTO service_history (customer_id, service_name, date, cost) VALUES (1, 'Plumbing', '2024-07-01', 100.0);
INSERT INTO service_history (customer_id, service_name, date, cost) VALUES (1, 'Electrical Repairs', '2024-07-05', 150.0);
INSERT INTO service_history (customer_id, service_name, date, cost) VALUES (2, 'Cleaning', '2024-07-10', 80.0);