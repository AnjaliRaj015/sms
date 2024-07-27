USE sms;

INSERT INTO users (full_name, username, password, role, email, phone, address) VALUES
('John Doe', 'john_doe', 'pass1234', 'customer', 'john@example.com', '123-456-7890', 'Elm Street'),
('Jane Doe', 'jane_doe', 'admin1234', 'staff', 'jane@example.com', '987-654-3210', 'Elm Street'),
('Alice Johnson', 'alice_j', 'alice1234', 'customer', 'alice@example.com', '234-567-8901', 'Maple Avenue'),
('Bob Smith', 'bob_s', 'bob1234', 'customer', 'bob@example.com', '345-678-9012', 'Oak Lane'),
('Charlie Brown', 'charlie_b', 'charlie1234', 'customer', 'charlie@example.com', '456-789-0123', 'Pine Road'),
('David Williams', 'david_w', 'david1234', 'customer', 'david@example.com', '567-890-1234', 'Cedar Street'),
('Emma Watson', 'emma_w', 'emma1234', 'customer', 'emma@example.com', '678-901-2345', 'Birch Boulevard'),
('Frank Miller', 'frank_m', 'frank1234', 'staff', 'frank@example.com', '789-012-3456', 'Walnut Way'),
('Grace Lee', 'grace_l', 'grace1234', 'staff', 'grace@example.com', '890-123-4567', 'Willow Drive'),
('Henry Adams', 'henry_a', 'henry1234', 'staff', 'henry@example.com', '901-234-5678', 'Hickory Hill'),
('Isabella Moore', 'isabella_m', 'isabella1234', 'staff', 'isabella@example.com', '012-345-6789', 'Spruce Street'),
('Jack Davis', 'jack_d', 'jack1234', 'staff', 'jack@example.com', '123-456-7891', 'Cherry Lane');


-- Insert sample data into customers
INSERT INTO customers (user_id, username, password, full_name, email, phone, address) VALUES
(1, 'john_doe', 'pass1234', 'John Doe', 'john@example.com', '123-456-7890', 'Elm Street'),
(3, 'alice_j', 'alice1234', 'Alice Johnson', 'alice@example.com', '234-567-8901', 'Maple Avenue'),
(4, 'bob_s', 'bob1234', 'Bob Smith', 'bob@example.com', '345-678-9012', 'Oak Lane'),
(5, 'charlie_b', 'charlie1234', 'Charlie Brown', 'charlie@example.com', '456-789-0123', 'Pine Road'),
(6, 'david_w', 'david1234', 'David Williams', 'david@example.com', '567-890-1234', 'Cedar Street'),
(7, 'emma_w', 'emma1234', 'Emma Watson', 'emma@example.com', '678-901-2345', 'Birch Boulevard');
-- Insert sample data into staff
INSERT INTO staffs (user_id, username, password, full_name, email, phone, address) VALUES
(2, 'jane_doe', 'admin1234', 'Jane Doe', 'jane@example.com', '987-654-3210', 'Elm Street'),
(8, 'frank_m', 'frank1234', 'Frank Miller', 'frank@example.com', '789-012-3456', 'Walnut Way'),
(9, 'grace_l', 'grace1234', 'Grace Lee', 'grace@example.com', '890-123-4567', 'Willow Drive'),
(10, 'henry_a', 'henry1234', 'Henry Adams', 'henry@example.com', '901-234-5678', 'Hickory Hill'),
(11, 'isabella_m', 'isabella1234', 'Isabella Moore', 'isabella@example.com', '012-345-6789', 'Spruce Street'),
(12, 'jack_d', 'jack1234', 'Jack Davis', 'jack@example.com', '123-456-7891', 'Cherry Lane');
INSERT INTO services (name, description, estimated_duration, cost) VALUES
('Plumbing', 'Fixing leaks and installing pipes', 60, 50.0),
('Electrical Repairs', 'Fixing electrical issues', 90, 70.0),
('Cleaning', 'House cleaning services', 120, 100.0);
