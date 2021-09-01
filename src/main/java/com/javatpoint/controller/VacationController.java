package com.javatpoint.controller;

import com.javatpoint.model.Employee;
import com.javatpoint.model.Vacations;
import com.javatpoint.service.VacationService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/HR/vacations")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @PostMapping(value = "/add-vacation")
    public ResponseEntity addUser(@RequestBody Vacations vacations) throws Exception {
        vacationService.addVacation(vacations);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get-vacations")
    public ResponseEntity<Object> getVacations(@RequestBody String name) throws Exception {
        int vacations = vacationService.getVacations(name);
        System.out.println(vacations);
        return ResponseEntity.ok().build();
    }
}
