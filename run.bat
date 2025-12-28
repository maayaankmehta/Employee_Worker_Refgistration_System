@echo off
echo Starting Employer-Worker Registration System...
echo.

REM Run the application
java -cp "bin;lib\mysql-connector-java-8.0.33.jar" com.employeemanagement.Main

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Application exited with errors.
    pause
)
