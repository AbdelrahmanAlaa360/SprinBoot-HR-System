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

    @PostMapping(value = "/add/{id}")
    public ResponseEntity addVacation(@PathVariable("id") Integer employeeId) {
        salaryHistoryService.addSalary(employeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-salary/{id}")
    public ResponseEntity getSalaryHistory(@PathVariable("id") Integer employeeId) {
        salaryHistoryService.getSalaryHistories(employeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-specific-month-salary/{employeeId}/{month}/{year}")
    public ResponseEntity getSpecificMonthSalary(@PathVariable("employeeId") Integer employeeId, @PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        SalaryHistory salaryHistory = salaryHistoryService.getSalaryOfSpecificMonth(month, year, employeeId);
        return ResponseEntity.ok(salaryHistory);
    }

}
