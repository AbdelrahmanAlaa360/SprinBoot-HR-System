package com.javatpoint.repository;

import com.javatpoint.model.Employee;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DbInit implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Employee employee = new Employee();
        employee.setName("Ahmed");
        employee.setJoinYear(2019);
        employee.setGradDate(2018);
        employee.setNetSalary(10000);
        employee.setGrossSalary(11000);
        employee.setDepartment("CS");
        employee.setExperience("Moderate");
        employee.setGender("Male");
        employee.setManagerName("Abbas");
        employee.setBirthDate(2000);
        employee.setTeamName("Team 1");
    }
}


