package com.javatpoint.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Vacations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String employee_name;
    private int year;
    private int exceeded;
    private int employeeId;

    public Vacations(Integer id, String employee_name, int year, int exceeded, int employeeId) {
        this.id = id;
        this.employee_name = employee_name;
        this.year = year;
        this.exceeded = exceeded;
        this.employeeId = employeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Vacations() {

    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getExceeded() {
        return exceeded;
    }

    public void setExceeded(int exceeded) {
        this.exceeded = exceeded;
    }
}
