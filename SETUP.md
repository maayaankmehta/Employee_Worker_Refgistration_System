# Employer-Worker Registration System Setup Guide

## Quick Setup Instructions

### Step 1: Download MySQL Connector

1. Visit: https://dev.mysql.com/downloads/connector/j/
2. Select "Platform Independent" from the dropdown
3. Download the ZIP archive (mysql-connector-j-8.x.x.zip)
4. Extract the ZIP file
5. Copy `mysql-connector-j-8.x.x.jar` to the `lib/` folder in this project
6. Rename it to `mysql-connector-java-8.0.33.jar` (or update the filename in compile.bat and run.bat)

### Step 2: Setup MySQL Database

1. Open MySQL Workbench or MySQL command line
2. Run the following commands:

```sql
-- Or simply execute the schema file:
SOURCE d:/A/java/project/employeeworkerreg/sql/schema.sql;
```

### Step 3: Configure Database Password

1. Open: `src/com/employeemanagement/dao/DatabaseConnection.java`
2. Update line 8 with your MySQL password:
   ```java
   private static final String PASSWORD = "your_password_here";
   ```

### Step 4: Compile and Run

**Option 1: Using Batch Scripts (Recommended)**
```bash
# Compile the project
compile.bat

# Run the application
run.bat
```

**Option 2: Manual Commands**
```bash
# Compile
javac -cp "lib\mysql-connector-java-8.0.33.jar" -d bin src\com\employeemanagement\**\*.java

# Run
java -cp "bin;lib\mysql-connector-java-8.0.33.jar" com.employeemanagement.Main
```

## Common Issues

**Issue**: ClassNotFoundException
- **Solution**: Ensure MySQL Connector JAR is in the `lib/` folder

**Issue**: Database connection failed
- **Solution**: Check MySQL is running and password is correct in DatabaseConnection.java

**Issue**: Database does not exist
- **Solution**: Run the schema.sql file in MySQL first

## Testing the Application

1. Start with the **Departments** tab - Add a few departments (e.g., IT, HR, Finance)
2. Move to **Employers** tab - Register employers and assign them to departments
3. Go to **Workers** tab - Register workers under employers
4. Check **View Records** tab - See all data together with search functionality

Enjoy using the Employer-Worker Registration System!
