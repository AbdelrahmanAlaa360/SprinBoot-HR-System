package com.javatpoint.service;

import com.javatpoint.model.Vacations;
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

    final String DB_URL = "jdbc:mysql://localhost/phase1";
    final String USER = "root";
    final String PASS = "12345";

    public Vacations addVacation(Vacations vacations) throws Exception {
        Date dt = new Date();
        int currentYear = dt.getYear() + 1900;

        String name = vacations.getEmployee_name();
        String Query = "SELECT COUNT(*) FROM vacations WHERE employee_name = '" + name + "' and year = '"+currentYear+"'";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(Query);
        rs.next();
        int leaves = rs.getInt(1), joinYear = 0;
        int exceed = 0;
        if(leaves > 21 ){
            if(leaves <= 30 && currentYear - joinYear >= 10){
                exceed = 0;
            }
            else {
                exceed= 1;
            }
        }
        vacations.setExceeded(exceed);
        vacationRepository.save(vacations);
        return vacations;
    }


    public int getVacations(String name) throws Exception {
        String Query = "SELECT COUNT (*) FROM vacations WHERE employee_name = '" + name + "' AND exceeded = 1";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(Query);
        rs.next();
        int leaves = rs.getInt(1);
        return leaves;
    }


}
