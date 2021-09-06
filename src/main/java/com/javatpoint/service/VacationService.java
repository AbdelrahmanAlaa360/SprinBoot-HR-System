package com.javatpoint.service;

import com.javatpoint.model.Vacations;
import com.javatpoint.repository.UserRepository;
import com.javatpoint.repository.vacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

@Service
public class VacationService {

    @Autowired
    public vacationRepository vacationRepository;
    @Autowired
    public UserRepository userRepository;

    public Vacations addVacation(Vacations vacations) throws Exception {
        Date dt = new Date();
        int currentYear = dt.getYear() + 1900;
        String name = vacations.getEmployee_name();
        int employeeId = vacations.getEmployeeId();
        int currentVacations = vacationRepository.countCurrentYearVacations(employeeId, currentYear);
        int joinYear = userRepository.getJoinYear(employeeId);
        int exceed = 0;

        if (currentVacations >= 21) {
            if (currentVacations <= 30 && currentYear - joinYear >= 10) {
                exceed = 0;
            } else {
                exceed = 1;
            }
        }
        vacations.setExceeded(exceed);
        vacationRepository.save(vacations);
        return vacations;
    }


    public int getVacations(Integer id) throws Exception {
        Date dt = new Date();
        int currentYear = dt.getYear() + 1900;
        int exceededLeaves = vacationRepository.countExceededVacations(id, currentYear);
        return exceededLeaves;
    }


}
