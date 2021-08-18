package com.javatpoint.model;

public class Salary {
    private double netSalary;
    private double grossSalary;

    public Salary(Employee employee) {
        if(employee != null){
            this.grossSalary = employee.grossSalary;
            this.netSalary = employee.netSalary;
        }
    }

    public String toString(){
            return "Gross Salary = " + this.grossSalary + "\nNet Salary = " + this.netSalary;
    }

    public Salary() {
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }
}
