package com.motorph;

import java.util.Scanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;

public class App 
{
    public static int employeeNumber;
    public static String employeeName;
    public static String employeeBirthday;
    public static int employeeHoursWorked;
    public static double employeeHourlyRate;

    public static void main( String[] args )
    {
        System.out.println("===INPUTS===========================");
        // Inputs
        Scanner scanner = new Scanner(System.in);
        System.out.print("Employee Number: ");
        employeeNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Employee Name: ");
        employeeName = scanner.nextLine();

        System.out.print("Birthday: ");
        employeeBirthday = scanner.nextLine();

        System.out.print("Hours Worked: ");
        employeeHoursWorked = scanner.nextInt();
        scanner.nextLine();
        System.out.println("====================================");

        // Start - Gather Data from Excel File
        String relativeFilePath = "src/main/java/resources/input/data.xlsx";
        String absoluteFilePath = Paths.get(relativeFilePath).toAbsolutePath().toString();
        
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try (FileInputStream inputStream = new FileInputStream(absoluteFilePath)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                boolean foundEmployeeNumber = false;
                boolean foundEmployeeName = false;
                boolean foundEmployeeBirthday = false;

                for (Cell cell : row) {
                    if (!foundEmployeeNumber && (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)) {
                        double cellValue = cell.getNumericCellValue();
                        int intValue = (int) cellValue;
                        if (intValue == employeeNumber) {
                            foundEmployeeNumber = true;
                        }
                    }

                    if (!foundEmployeeName && (cell.getCellType() == CellType.STRING)) {
                        String cellValue = cell.getStringCellValue();
                        if (cellValue.equals(employeeName)) {
                            foundEmployeeName = true;
                        }
                    }

                    if (!foundEmployeeBirthday && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        String cellValue = dateFormat.format(cell.getDateCellValue());
                        if (cellValue.equals(employeeBirthday)) {
                            foundEmployeeBirthday = true;
                        }
                    }

                    if (foundEmployeeNumber && foundEmployeeName && row.getLastCellNum() > 0) {
                        Cell lastCell = row.getCell(18); //cellnum 18 is the hourly rate column
                        if (lastCell != null) {
                            employeeHourlyRate = lastCell.getNumericCellValue();
                        }
                    }
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // End - Gather Data from Excel File
        
        scanner.close();
        Employee employee = new Employee(employeeNumber, employeeName, employeeBirthday, employeeHoursWorked, employeeHourlyRate);
        
        // Display results
        System.out.println("===DISPLAY==========================");
        System.out.println("Employee Number: " + employee.getEmployeeNumber());
        System.out.println("Employee Name: " + employee.getEmployeeName());
        System.out.println("Birthday: " + employee.getBirthday());
        System.out.println("Hours Worked: " + employee.getHoursWorked());
        System.out.println("Gross Weekly Salary: $" + employee.getGrossSalary());
        System.out.println("Net Weekly Salary: $" + employee.getNetSalary());
        System.out.println("====================================");
    }
}
