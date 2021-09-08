package com.javatpoint.controller;

import com.javatpoint.model.SalaryHistory;
import com.javatpoint.model.Vacations;
import com.javatpoint.service.SalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/HR/salary-history")
public class SalaryHistoryController {
    @Autowired
    private SalaryHistoryService salaryHistoryService;

    @PostMapping(value = "/add")
    public ResponseEntity addVacation(@RequestBody Integer employeeId) {
        salaryHistoryService.addSalary(employeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-salary")
    public ResponseEntity getSalaryHistory(@RequestBody Integer employeeId){
        salaryHistoryService.getSalaryHistories(employeeId);
        return ResponseEntity.ok().build();
    }

}
