package com.javatpoint.springbootexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.model.Employee;
import com.javatpoint.model.Salary;
import com.javatpoint.service.EmployeeService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import javax.transaction.Transactional;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeService employeeService;

    @Test
    public void addEmployee() {
        Employee userRecord  = new Employee();
        userRecord.setName("Sayed");
        userRecord.setBirthDate(2000);
        userRecord.setDepartment("CS");
        userRecord.setExperience("High");
        userRecord.setGender("Male");
        userRecord.setTeamName("Team 51");
        userRecord.setGrossSalary(7900);
        userRecord.setNetSalary(7700);
        userRecord.setManagerName("Tarek");
        userRecord.setGradDate(2019);
        Employee result = employeeService.addUser(userRecord);
    }

    @Test
    public void deleteEmployee() throws NotFoundException {
        int id = 5;
        Employee userRecord = employeeService.getUserById(id);
        System.out.println(userRecord.getId());
        employeeService.deleteUser(userRecord.getId());
        assertEquals(employeeService.existsById(userRecord.getId()), false);
    }

    @Test
    public void getEmployeeInfo() throws NotFoundException {
        int id = 1;
        Employee result = employeeService.getUserById(1);
        assertEquals(result.getId(), 1);
        //System.out.println(result);
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        int id = 2;
        Employee employee = new Employee();
        employee.setName("Abdo");
        employee.setGrossSalary(15000);
        employee.setNetSalary(14000);

        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setName("Abdelrahman");

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/update-user").
                param("id",String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());

//        String query = String.valueOf(employer.getId());
//        Employee update = employeeService.updateUser(employer, query);
//        assertThat(update).usingRecursiveComparison().isEqualTo(employer);
    }

    @Test
    @Transactional
    public void getEmployeeSalary() throws Exception {
        int id = 2;
//      Employee employee = employeeService.getUserById(id);
//      Salary salary = new Salary(employee);
//      assertEquals(employee.getGrossSalary(), 1500);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(3);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-salary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(3)))
                .andExpect(status().isOk());
    }


    @Test
    public void getAllEmployeesUnderManager() throws SQLException {
        String managerEmployee = "Abbas";

        final String DB_URL = "jdbc:mysql://localhost/phase1";
        final String USER = "root";
        final String PASS = "12345";
        String Query = "SELECT DISTINCT manager_name FROM employee WHERE manager_name = '"+managerEmployee+"'";

        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(Query);
        System.out.println("====================\n\n");

        while(rs.next()){
            String employeeManager = rs.getString("manager_name");
            Connection conn2 = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt2 = conn2.createStatement();
            String Query2 = "SELECT * FROM employee WHERE manager_name = '"+employeeManager+"'";
            ResultSet rs2 = stmt2.executeQuery(Query2);
            while(rs2.next()){
                String employee = rs2.getString("name");
                System.out.println(employee);
                Connection conn3 = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt3 = conn3.createStatement();
                String Query3 = "SELECT * FROM employee WHERE manager_name = '"+employee+"'";
                ResultSet rs3 = stmt3.executeQuery(Query3);
                while(rs3.next()) {
                    System.out.println(rs3.getString("name"));
                }
            }
            //System.out.println(rs.getString("manager_name"));
        }

        System.out.println("====================\n\n");
    }

}
