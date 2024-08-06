# Service Management System

This is a Service Management System (SMS) application developed in Java using JavaFX for the user interface and MySQL for the database. The application supports various services such as plumbing, electrical repairs, and cleaning. It provides functionalities for user management, service management, appointment management, and reporting.

## Table of Contents

- Features
- Modules
- Installation
- Running the Application
- Usage
- Database Schema

## Features

- **User Management**: Manage customer and staff information.
- **Service Management**: Add and manage services offered by the company.
- **Appointment Management**: Schedule and manage service appointments.
- **Reporting**: Generate reports on revenue and workload distribution.
- **Authentication**: User login and role-based access control.

## Modules

### 1. User Management

Handles user registration, login, and role-based access control.

**Classes:**
- `UserDAO`: Data Access Object for user-related operations.
- `LoginForm`: JavaFX form for user login.
- `RegisterForm`: JavaFX form for user registeration.
- `User`: Model class representing a user.

### 2. Service Management

Manages services offered by the company, allowing staff to add and view services.

**Classes:**
- `ServiceDAO`: Data Access Object for service-related operations.
- `ServiceForm`: JavaFX form for adding new services.
- `Service`: Model class representing a service.

### 3. Appointment Management

Allows staff to schedule, view, and manage appointments.

**Classes:**
- `AppointmentDAO`: Data Access Object for appointment-related operations.
- `AppointmentForm`: JavaFX form for managing appointments.
- `Appointment`: Model class representing an appointment.

### 4. Reporting

Generates reports on revenue and workload distribution.

**Classes:**
- `ReportDAO`: Data Access Object for report-related operations.
- `ReportForm`: JavaFX form for displaying reports.

### 5. Service History

Keeps a record of completed service appointments.

**Classes:**
- `ServiceHistoryDAO`: Data Access Object for service history operations.
- `ServiceHistoryForm`: JavaFX form for viewing service history.
- `ServiceHistory`: Model class representing a service history entry.
- 
### 6. Quote Request

Keeps a record of the quote requets.

**Classes:**
- `QuoteRequestDAO`: Data Access Object for quote request operations.
- `QuoteRequest`: Model class representing a QuoteRequest entry.
  
## Installation (if possible easiest method will be the OR step mentioned below please check it out)

### Prerequisites

- [Java JDK 8 or higher](https://youtu.be/4v6ua2_S5jI?si=ChwlvBHp0JmO53Rh](https://youtu.be/0dnVwuAHGx0?si=F1r8PH8gyECUZ0hR))
- [MySQL Server & mysql connector](https://youtu.be/YbOLy2LtsjM?si=qbCI8suQZIBiJjwA](https://youtu.be/ZhljL_0gm04?si=zBTloWwPpiqe5Z7Q))
- [Maven](https://youtu.be/YTvlb6eny_0?si=pbexveUBjwRicDbD)
- [JavaFX SDK](https://youtu.be/I4J5_IWDeEo?si=lkxYnT6p-g4s6H1A)

### Steps

1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/service-management-system.git
    cd service-management-system
    ```

2. **Set up the database:**
    - Create a MySQL database.
    - Run the SQL scripts located in the `sms-fx/sms/src/resources/db` folder to create tables and insert initial data.

3. **Configure the database connection:**
    - Update the database connection settings in `src/main/java/com/main/db/database.java`.

4. **Build the project:**
    ```bash
    mvn clean install
    ```

## Running the Application

1. **Run the main application:**
    ```bash
    mvn javafx:run
    ```
## OR [java project setup in vscode](https://youtu.be/NYGHL8N6Kc8?si=zUVuh24rv98Uf5cQ)
- download zip folder and extract files
- In you vscode command palette make a new java project elect javafx project
- type com.main
- type sms and enter
- copy paste the contents of extracted project folder
- take main.java file top right corner choose run java
   
2. **Login as a user:**
    - Use the login form to authenticate as either a staff member or a customer.
    
## Usage

### Staff Interface

- **Home Tab**: View reports on revenue and workload distribution.
- **Service Management Tab**: Add and manage services.
- **Appointment Management Tab**: Schedule and manage appointments.
- **Customer Management Tab**: Add and manage customer information.
- **Incoming Requests Tab**: View and manage incoming service requests.

### Customer Interface

- **Profile Tab**: View and edit user profile details.
- **Services Tab**: Browse available services.
- **Quote Requests Tab**: Request quotes for services.
- **Appointments Tab**: View and reschedule upcoming appointments.
- **Pending Requests Tab**: View pending service requests.
- **Completed Requests Tab**: View history of completed service requests.

## Database Schema

### Tables

- `users`: Stores user information.
- `customer`: Stores customer information.
- `staff`: Stores staff information.
- `services`: Stores service information.
- `appointments`: Stores appointment information.
- `service_history`: Stores history of completed services.
- `quote_requests`: Stores service quote requests.

### please refer resources for schema and sample data input copy paste and run in mysql server to build a db

