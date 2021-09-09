package com.javatpoint.controller;

import com.javatpoint.model.Salary;
import com.javatpoint.repository.UserRepository;
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
    @Autowired
    public UserRepository userRepository;

    @RequestMapping("/HR")
    public ResponseEntity<Employee> getAllUser(int id) throws NotFoundException {
        employeeService.getUserById(id);
        return ResponseEntity.ok().build();
    }

    /*@PostMapping(value = "/update-user")
    public ResponseEntity updateUser(int id) {
        Employer newEmployee = employeeService.getEmployeeInfo
    }*/

    @PutMapping(value = "/update-user/{id}")
    public ResponseEntity updateUser(@RequestBody Employee employee, @PathVariable("id") Integer id) throws NotFoundException {
        employeeService.updateUser(employee, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add-user")
    public ResponseEntity addUser(@RequestBody Employee userRecord) throws Exception {
        employeeService.addUser(userRecord);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/delete-user")
    public void deleteUser(@RequestBody int id) {
        employeeService.deleteUser(id);
    }

    @GetMapping(value = "/get-salary")
    public ResponseEntity<Object> getEmployeeSalary(@RequestBody int id) throws NotFoundException {
        Salary salary = employeeService.getSalary(id);
        System.out.println(salary);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/raise-salary/{id}")
    public ResponseEntity<Object> raiseSalary(@RequestBody Integer raise, @PathVariable("id") Integer id) throws NotFoundException {
        employeeService.raiseSalary(raise, id);
        return ResponseEntity.ok().build();
    }


}