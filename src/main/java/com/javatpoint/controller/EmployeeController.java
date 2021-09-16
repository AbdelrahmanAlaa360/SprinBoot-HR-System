package com.javatpoint.controller;

import com.javatpoint.model.Salary;
import com.javatpoint.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javatpoint.model.Employee;
import com.javatpoint.service.EmployeeService;

import java.util.List;

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

    @GetMapping(value = "/get-employee/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable("id") Integer id) throws NotFoundException {
        Employee employee = employeeService.getUserById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping(value = "/delete-user")
    public void deleteUser(@RequestBody int id) {
        employeeService.deleteUser(id);
    }

    @GetMapping(value = "/get-salary/{id}")
    public ResponseEntity<Object> getEmployeeSalary(@PathVariable("id") Integer id) throws NotFoundException {
        Salary salary = employeeService.getSalary(id);
        return ResponseEntity.ok(salary);
    }

    @GetMapping(value = "/get-employees-in-team")
    public ResponseEntity<Object> getEmployeesInTeam(@RequestBody String teamName) {
        List<String> l = employeeService.getEmployeesInTeam(teamName);
        return ResponseEntity.ok(l);
    }

    // All Employees Under Manager
    @GetMapping(value = "/get-employees-under-manager")
    public ResponseEntity<Object> getAllEmployeesUnderManager(@RequestBody Integer managerId) {
        List<Employee> l = employeeService.getAllEmployeesUnderManager(managerId);
        return ResponseEntity.ok(l);
    }

    @GetMapping(value = "/get-employees-under-specific-manager")
    public ResponseEntity<Object> getEmployeesUnderSpecificManager(String name){
        List<String> l = employeeService.getEmployeesUnderSpecificManager(name);
        return ResponseEntity.ok(l);
    }

    @PutMapping(value = "/raise-salary/{id}/{raise}")
    public ResponseEntity<Object> raiseSalary(@PathVariable("raise") Integer raise, @PathVariable("id") Integer id) throws NotFoundException {
        employeeService.raiseSalary(raise, id);
        return ResponseEntity.ok().build();
    }


}