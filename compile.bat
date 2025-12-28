@echo off
echo Compiling Employer-Worker Registration System...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all Java files
javac -cp "lib\mysql-connector-java-8.0.33.jar" -d bin src\com\employeemanagement\model\*.java src\com\employeemanagement\dao\*.java src\com\employeemanagement\ui\*.java src\com\employeemanagement\Main.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo.
    echo To run the application, execute: run.bat
) else (
    echo Compilation failed! Please check the errors above.
)

pause
