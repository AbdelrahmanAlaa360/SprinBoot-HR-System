package com.javatpoint.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SalaryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String employee_name;
    private double tax_and_insurance;
    private double bonus;
    private double gross_salary;
    private double net_salary;
    private Integer employee_id;
    private Integer month;
    private Integer year;
    private double deductions;

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public SalaryHistory() {
    }

    public SalaryHistory(Integer id, String employee_name, double tax_and_insurance, double bonus, double gross_salary, double net_salary, Integer employee_id, Integer month, Integer year, double deductions) {
        this.id = id;
        this.employee_name = employee_name;
        this.tax_and_insurance = tax_and_insurance;
        this.bonus = bonus;
        this.gross_salary = gross_salary;
        this.net_salary = net_salary;
        this.employee_id = employee_id;
        this.month = month;
        this.year = year;
        this.deductions = deductions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public double getTax_and_insurance() {
        return tax_and_insurance;
    }

    public void setTax_and_insurance(double tax_and_insurance) {
        this.tax_and_insurance = tax_and_insurance;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getGross_salary() {
        return gross_salary;
    }

    public void setGross_salary(double gross_salary) {
        this.gross_salary = gross_salary;
    }

    public double getNet_salary() {
        return net_salary;
    }

    public void setNet_salary(double net_salary) {
        this.net_salary = net_salary;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
