package com.javatpoint.model;
import javax.persistence.*;

@Entity
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public String name;
    public String gender;
    public int birthDate;
    public int gradDate;
    public String experience;
    public String department;
    public int grossSalary;
    public int netSalary;
    public String teamName;
    public String managerName;

    public Employee(int id, String name, String gender, int birthDate, int gradDate, String experience, String department, int grossSalary, int netSalary, String teamName, String managerName) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.gradDate = gradDate;
        this.experience = experience;
        this.department = department;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.teamName = teamName;
        this.managerName = managerName;
    }

    public static Employee updateEmployee(Employee oldEmployer, Employee newEmployer) {
        if(newEmployer.name != null)oldEmployer.name = newEmployer.name;
        if(newEmployer.gender != null)oldEmployer.gender = newEmployer.gender;
        if(newEmployer.birthDate != 0)oldEmployer.birthDate = newEmployer.birthDate;
        if(newEmployer.gradDate != 0)oldEmployer.gradDate = newEmployer.gradDate;
        if(newEmployer.experience != null)oldEmployer.experience = newEmployer.experience;
        if(newEmployer.department != null)oldEmployer.department = newEmployer.department;
        if(newEmployer.grossSalary != 0)oldEmployer.grossSalary = newEmployer.grossSalary;
        if(newEmployer.netSalary != 0)oldEmployer.netSalary = newEmployer.netSalary;
        if(newEmployer.teamName != null)oldEmployer.teamName = newEmployer.teamName;
        if(newEmployer.managerName != null)oldEmployer.managerName = newEmployer.managerName;
        return oldEmployer;
    }

    public Employee() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public int getGradDate() {
        return gradDate;
    }

    public void setGradDate(int gradDate) {
        this.gradDate = gradDate;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(int grossSalary) {
        this.grossSalary = grossSalary;
    }

    public int getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(int netSalary) {
        this.netSalary = netSalary;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}