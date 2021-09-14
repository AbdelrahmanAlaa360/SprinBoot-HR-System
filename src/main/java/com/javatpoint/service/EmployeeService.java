package com.javatpoint.service;

import com.javatpoint.Exceptions.EmployeeNotFoundException;
import com.javatpoint.Exceptions.NegativeSalaryException;
import com.javatpoint.model.Salary;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javatpoint.model.Employee;
import com.javatpoint.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.*;
import java.util.List;

import static com.javatpoint.model.Employee.updateEmployee;

@Service
public class EmployeeService {
    @Autowired
    public UserRepository userRepository;

    public Employee getUserById(int id) throws NotFoundException {
        Employee employee = userRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        return employee;
    }

    public Employee addUser(Employee userRecord) throws Exception {
        if (userRecord.netSalary <= 0) {
            double tax = 0.15, insurance = 500;
            double netSalary = userRecord.grossSalary - (userRecord.grossSalary * tax) - insurance;
            if (netSalary <= 0) {
                throw new NegativeSalaryException("Netsalary Can't be less than zero");
            }
            userRecord.setNetSalary(netSalary);
        } else {
            throw new NegativeSalaryException("Gross Salary Must be greater than zero");
        }
        userRepository.save(userRecord);
        return userRecord;
    }

    public void deleteUser(int userId) {
        Employee employee = userRepository.findById(userId).orElse(null);
        if(employee == null){
            throw new EmployeeNotFoundException("Employee Does Not Exist");
        }
        userRepository.deleteById(userId);
    }

    public boolean existsById(Integer id) {
        Employee employee = userRepository.findById(id).orElse(null);
        if (employee == null) {
            return false;
        }
        return userRepository.existsById(id);
    }

    @Transactional
    public Employee updateUser(Employee updatedEmployee, Integer oldEmployeeId) throws NotFoundException {
        if(!existsById(oldEmployeeId)){
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        Employee originalEmployee = userRepository.getEmployee(oldEmployeeId);
        originalEmployee = updateEmployee(updatedEmployee, originalEmployee);
        return userRepository.save(originalEmployee);
    }

    public Salary getSalary(int id) throws NotFoundException {
        if(!existsById(id)){
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        Employee employee = getUserById(id);
        return new Salary(employee);
    }

    public List<Employee> getAllEmployeesUnderManager(Integer managerId) {
        return userRepository.getAllEmployees(managerId);
    }

    public List<String> getEmployeesUnderSpecificManager(String managerName) {
        return userRepository.getEmployeesUnderSpecificManager(managerName);
    }

    public List<String> getEmployeesInTeam(String teamName) {
        List<String> employeesInTeam = userRepository.getEmployeesInTeam(teamName);
        return employeesInTeam;
    }

    final String DB_URL = "jdbc:mysql://localhost/phase1";
    final String USER = "root";
    final String PASS = "12345";

    public void removeManager(Integer oldManager) throws SQLException {

        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        String newManager = userRepository.getManagerName(oldManager);
        if (newManager != "NULL") {
            String Query = "SELECT * FROM employee WHERE manager_name = '" + oldManager + "'";
            ResultSet rs = stmt.executeQuery(Query);
            while (rs.next()) {
                String Query2 = "UPDATE employee SET manager_name = '" + newManager + "' WHERE manager_name = '" + oldManager + "'";
                Statement stmt2 = conn.createStatement();
                stmt2.executeUpdate(Query2);
            }
            Query = "DELETE FROM employee WHERE name = '" + oldManager + "'";
            stmt.executeUpdate(Query);
            System.out.println("Manager Deleted Succesfully");
        } else {
            System.out.println("Can not Delete This Manager");
        }
    }

    public Employee raiseSalary(Integer raise, Integer employeeId) {
        if(!existsById(employeeId)){
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        Employee employee = userRepository.getById(employeeId);
        double grossSalary = employee.getGrossSalary();
        employee.setGrossSalary(grossSalary + raise);
        return userRepository.save(employee);
    }

}