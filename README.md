# Employer-Worker Registration System

A comprehensive Java desktop application for managing employer and worker registrations with department assignments using MySQL database.

## Features

- **Department Management**: Create, update, delete, and view departments
- **Employer Registration**: Register employers with company details and assign them to departments
- **Worker Registration**: Register workers under employers with department assignments
- **Comprehensive View**: Search and view all worker records with their employer and department information
- **User-Friendly GUI**: Built with Java Swing featuring a modern Nimbus look and feel
- **Data Validation**: Input validation to ensure data integrity
- **CRUD Operations**: Complete Create, Read, Update, Delete functionality for all entities

## Technology Stack

- **Language**: Java
- **UI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J

## Database Schema

The system uses three main tables:

- **departments**: Department information (id, name, description)
- **employers**: Employer details with department association
- **workers**: Worker information linked to employers and departments

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server 8.0 or higher
- MySQL Connector/J JAR file (mysql-connector-java-8.0.33.jar or later)

## Setup Instructions

### 1. Database Setup

1. Start your MySQL server
2. Open MySQL command line or MySQL Workbench
3. Execute the schema file:
   ```sql
   source d:/A/java/project/employeeworkerreg/sql/schema.sql
   ```
   Or manually run the SQL commands from `sql/schema.sql`

### 2. Configure Database Connection

1. Open `src/com/employeemanagement/dao/DatabaseConnection.java`
2. Update the following constants if needed:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/employee_worker_db";
   private static final String USER = "root";
   private static final String PASSWORD = ""; // Enter your MySQL password
   ```

### 3. Add MySQL Connector

1. Download MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/
2. Extract the JAR file
3. Copy it to the `lib/` directory in your project
4. Add the JAR to your classpath

### 4. Compile the Project

Open a terminal in the project directory and run:

```bash
javac -cp "lib/mysql-connector-java-8.0.33.jar" -d bin src/com/employeemanagement/**/*.java
```

### 5. Run the Application

```bash
java -cp "bin;lib/mysql-connector-java-8.0.33.jar" com.employeemanagement.Main
```

**Note**: On Linux/Mac, use `:` instead of `;` as the classpath separator.

## Usage Guide

### Departments Tab
- Add new departments with name and description
- View all departments in a table
- Update or delete existing departments
- Departments are used when registering employers and workers

### Employers Tab
- Register employers with personal and company information
- Assign employers to departments
- View, update, or delete employer records
- Cannot delete an employer if they have registered workers

### Workers Tab
- Register workers under an employer
- Assign workers to departments
- Set position and hire date
- View, update, or delete worker records

### View Records Tab
- See all worker records with complete information
- Search across all fields
- Refresh data to see latest updates

## Project Structure

```
employeeworkerreg/
├── src/
│   └── com/
│       └── employeemanagement/
│           ├── model/              # Entity classes
│           ├── dao/                # Database access layer
│           ├── ui/                 # User interface components
│           └── Main.java           # Application entry point
├── lib/
│   └── mysql-connector-java-*.jar  # JDBC driver
├── sql/
│   └── schema.sql                  # Database schema
└── README.md
```

## Troubleshooting

### Database Connection Failed
- Ensure MySQL server is running
- Check username and password in `DatabaseConnection.java`
- Verify the database `employee_worker_db` exists
- Confirm MySQL is listening on port 3306

### ClassNotFoundException: com.mysql.cj.jdbc.Driver
- Ensure MySQL Connector JAR is in the `lib/` directory
- Verify the JAR is included in the classpath when running

### Cannot Delete Employer
- This is expected behavior if the employer has registered workers
- Delete all workers under that employer first, or reassign them

## Future Enhancements

- Export records to CSV/PDF
- Advanced search and filtering
- User authentication and roles
- Reporting and analytics dashboard
- Email notifications
- Backup and restore functionality

## License

This project is created for educational purposes.
