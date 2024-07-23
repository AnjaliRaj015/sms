CREATE DATABASE IF NOT EXISTS sms;
USE sms;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('customer', 'staff', 'admin')),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255)

);


CREATE TABLE services (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    estimated_duration INT NOT NULL,
    cost DOUBLE NOT NULL
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL

);

CREATE TABLE staffs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL

);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    service_id INT,
    staff_id INT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending','scheduled', 'completed', 'canceled')),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL,
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE SET NULL,
    FOREIGN KEY (staff_id) REFERENCES staffs(id) ON DELETE SET NULL,
    INDEX (customer_id),
    INDEX (service_id),
    INDEX (staff_id)
);


CREATE TABLE service_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    service_name VARCHAR(255),
    date DATE,
    cost DOUBLE,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE quote_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('pending', 'accepted', 'rejected')),
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    service_name VARCHAR(100), -- Optional: service name if needed
    customer_name VARCHAR(100), -- Optional: customer name if needed
    customer_address VARCHAR(255), -- Optional: customer address
    customer_phone VARCHAR(20), -- Optional: customer phone
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);
