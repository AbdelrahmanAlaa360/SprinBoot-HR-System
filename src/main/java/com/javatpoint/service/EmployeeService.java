package com.javatpoint.service;

import com.javatpoint.Exceptions.BadArgumentException;
import com.javatpoint.Exceptions.EmployeeNotFoundException;
import com.javatpoint.Exceptions.NegativeSalaryException;
import com.javatpoint.model.Salary;
import com.javatpoint.model.UsersAccount;
import com.javatpoint.repository.UsersAccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.javatpoint.model.Employee;
import com.javatpoint.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UsersAccountRepository usersAccountRepository;

    public Employee getUserById(int id) throws NotFoundException {
        Employee employee = userRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        return employee;
    }

    public Employee addUser(Employee userRecord) throws Exception {
        if(userRecord.getName() == null){
            throw new BadArgumentException("Name Can not be Empty");
        }
        if(userRecord.getDepartment() == null){
            throw new BadArgumentException("Department Can not be Empty");
        }
        if(userRecord.getExperience() == null){
            throw new BadArgumentException("Experience Can not be Empty");
        }
        if(userRecord.getGender() == null){
            throw new BadArgumentException("Gender Can not be Empty");
        }
        if(userRecord.getManagerName() == null){
            throw new BadArgumentException("Manager Name Can not be Empty");
        }
        if(userRecord.getTeamName() == null){
            throw new BadArgumentException("Team Name Can not be Empty");
        }
        if(userRecord.getJoinYear() < 2000){
            throw new BadArgumentException("Join Year Can not be Less than 2000");
        }
        if (userRecord.grossSalary >= 0) {
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

        Integer employeeId = userRecord.getId();

        /********* User Account Auto Generation **********/
        UsersAccount usersAccount = new UsersAccount();
        long unixTime = Instant.now().getEpochSecond();
        String userName = userRecord.getName() + unixTime;
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        int actualPassword = (int)(Math.random() * 10000001);  // 0 to 10000000
        String encryptedPassword = encoder.encode(Integer.toString(actualPassword));
        usersAccount.setUser_name(userName);
        usersAccount.setPassword(encryptedPassword);
        usersAccount.setRole("EMPLOYEE");
        usersAccount.setEmployyeeId(employeeId);
        /********* End User Account Auto Generation *********/

        System.out.println("\n\n==============");
        System.out.println("actualPassword:  " + actualPassword + "\nEncrypted:  " + encryptedPassword);

        //usersAccountRepository.save(usersAccount);
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

    public static Employee updateEmployee(Employee newEmployer, Employee oldEmployer) {
        if (newEmployer.name != null) oldEmployer.name = newEmployer.name;
        if (newEmployer.gender != null) oldEmployer.gender = newEmployer.gender;
        if (newEmployer.birthDate != 0) oldEmployer.birthDate = newEmployer.birthDate;
        if (newEmployer.gradDate != 0) oldEmployer.gradDate = newEmployer.gradDate;
        if (newEmployer.experience != null) oldEmployer.experience = newEmployer.experience;
        if (newEmployer.department != null) oldEmployer.department = newEmployer.department;
        if (newEmployer.grossSalary >= 0) {
            oldEmployer.grossSalary = newEmployer.grossSalary;
            oldEmployer.netSalary = newEmployer.grossSalary - (newEmployer.grossSalary * 0.15) - 500;
        }
        if (newEmployer.teamName != null) oldEmployer.teamName = newEmployer.teamName;
        if (newEmployer.managerName != null) oldEmployer.managerName = newEmployer.managerName;
        if (newEmployer.joinYear != 0) oldEmployer.joinYear = newEmployer.joinYear;
        return oldEmployer;
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