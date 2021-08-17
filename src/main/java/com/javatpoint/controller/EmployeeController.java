package com.javatpoint.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javatpoint.model.Employee;
import com.javatpoint.service.EmployeeService;

@RestController
@RequestMapping(value = "/HR")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/HR")
    public ResponseEntity<Employee> getAllUser(int id) throws NotFoundException {
        Employee employer= employeeService.getUserById(id);
        return  ResponseEntity.ok().build();
    }

    /*@PostMapping(value = "/update-user")
    public ResponseEntity updateUser(int id) {
        Employer newEmployee = employeeService.getEmployeeInfo
    }*/

    @PutMapping(value = "/update-user")
    public ResponseEntity updateUser(@RequestBody int id) throws NotFoundException {
        Employee employer = employeeService.getUserById(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping(value = "/add-user")
    public ResponseEntity addUser(@RequestBody Employee userRecord) {
        employeeService.addUser(userRecord);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/delete-user")
    public void deleteUser(@RequestBody int id) {
        employeeService.deleteUser(id);
    }
}