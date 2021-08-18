package com.javatpoint.controller;

import com.javatpoint.model.Department;
import com.javatpoint.model.Employee;
import com.javatpoint.service.DepartmentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/HR")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping(value = "/add-department")
    public ResponseEntity addEmployee(@RequestBody Department department) throws NotFoundException {
        Department department1 = departmentService.saveEmployee(department);
        return ResponseEntity.ok().build();
    }

}
