package com.motorph;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Employee {
    private int employeeNumber;
    private String employeeName;
    private String birthday;
    private int hoursWorked;
    private double hourlyRate;
    
    public Employee(int employeeNumber, String employeeName, String birthday, int hoursWorked, double hourlyRate) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.birthday = birthday;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getGrossSalary() {
        BigDecimal grossSalary = new BigDecimal(hoursWorked * hourlyRate).setScale(2, RoundingMode.HALF_UP);
        return grossSalary.doubleValue();
    }

    public double getNetSalary() {
        BigDecimal netSalary = new BigDecimal(this.getGrossSalary() * 0.8).setScale(2, RoundingMode.HALF_UP);
        return netSalary.doubleValue();
    }
}
