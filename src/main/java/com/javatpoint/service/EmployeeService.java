package com.javatpoint.service;

import com.javatpoint.model.Salary;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javatpoint.model.Employee;
import com.javatpoint.repository.UserRepository;

import java.sql.*;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    public UserRepository userRepository;

    public Employee getUserById(int id) throws NotFoundException {
        if (!userRepository.existsById(id))
            throw new NotFoundException("Not Found");
        return userRepository.getById(id);
    }

    public Employee addUser(Employee userRecord) throws Exception {
        if(userRecord.netSalary != 0) {
            double tax = 0.15, insurance = 500;
            double netSalary = userRecord.grossSalary - (userRecord.grossSalary * tax) - insurance;
            if(netSalary <= 0){
                throw new Exception("Netsalary Can't be less than zero");
            }
            userRecord.setNetSalary(netSalary);
        }
        else{
            throw new Exception("Gross Salary Must be greater than zero");
        }
        userRepository.save(userRecord);
        return userRecord;
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public boolean existsById(Integer id)  {
        return userRepository.existsById(id);
    }

    public Employee updateUser(Employee updatedEmployee, Employee originalEmployee) throws NotFoundException {
        Employee.updateEmployee(updatedEmployee, originalEmployee);
        return userRepository.save(originalEmployee);
    }

    public Salary getSalary(int id) throws NotFoundException{
        Employee employee = getUserById(id);
        return new Salary(employee);
    }

    public List<Employee> getAllEmployeesUnderManager(Integer managerId){
        return userRepository.getAllEmployees(managerId);
    }

    final String DB_URL = "jdbc:mysql://localhost/phase1";
    final String USER = "root";
    final String PASS = "12345";

    public void removeManager(String oldManager) throws SQLException {




//        String Query = "SELECT * FROM employee WHERE name = '"+oldManager+"'";
//        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(Query);
//        String newManager = null;
//        while(rs.next()){
//            newManager = rs.getString("manager_name");
//        }
//        if(newManager != "NULL") {
//            Query = "SELECT * FROM employee WHERE manager_name = '" + oldManager + "'";
//            rs = stmt.executeQuery(Query);
//            while (rs.next()) {
//                String Query2 = "UPDATE employee SET manager_name = '" + newManager + "' WHERE manager_name = '"+oldManager+"'";
//                Statement stmt2 = conn.createStatement();
//                stmt2.executeUpdate(Query2);
//            }
//            Query = "DELETE FROM employee WHERE name = '"+oldManager+"'";
//            stmt.executeUpdate(Query);
//            System.out.println("Manager Deleted Succesfully");
//        }
//        else{
//            System.out.println("Can not Delete This Manager");
//        }
    }

}