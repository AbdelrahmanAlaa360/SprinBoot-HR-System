package com.javatpoint.springbootexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.javatpoint.model.Employee;
import com.javatpoint.repository.UserRepository;
import com.javatpoint.repository.UsersAccountRepository;
import com.javatpoint.service.EmployeeService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;


import javax.transaction.Transactional;

import java.sql.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DatabaseSetup("/data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class EmployeeServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UsersAccountRepository usersAccountRepository;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/expectedAddEmployee.xml")
    public void addEmployee() throws Exception {
        Date dt = new Date();
        int year = dt.getYear() + 1900;

        Employee userRecord = new Employee();
        userRecord.setName("Mohammed");
        userRecord.setBirthDate(1988);
        userRecord.setDepartment("CS");
        userRecord.setExperience("Senior");
        userRecord.setGender("Male");
        userRecord.setTeamName("Team 3");
        userRecord.setGrossSalary(5000);
        userRecord.setManagerName("fathi");
        userRecord.setGradDate(2019);
        userRecord.setJoinYear(year);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(userRecord);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/add-user")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andDo(print());

//        int actualNetSalary = userRepository.getNetSalary(5);
//        assertEquals(actualNetSalary, 3750);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/expectedDeleteEmployee.xml")
    public void deleteEmployee() throws NotFoundException {
        int id = 4;
        Employee userRecord = employeeService.getUserById(id);
        System.out.println(userRecord.getId());
        employeeService.deleteUser(userRecord.getId());
        assertEquals(employeeService.existsById(userRecord.getId()), false);
    }

    @Test
    public void getEmployeeInfo() throws NotFoundException {
        int id = 4;
        Employee result = employeeService.getUserById(id);
        assertEquals(result.getId(), 4);
    }

    @Test
    @Transactional
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/expectedUpdateEmployee.xml")
    public void updateEmployee() throws Exception {
        Integer employeeId = 4;
        Employee employee = new Employee();
        employee.setGrossSalary(10000);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/update-user/" + employeeId)
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk());

    }

    @Test
    @Transactional
    public void getEmployeeSalary() throws Exception {
        int id = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(id);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-salary/" + id)
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllEmployeesUnderManager() throws Exception {
        Integer managerId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(managerId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-under-manager")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
        //List<Employee> employeesUnderManager = userRepository.getAllEmployees(managerId);
        //assertEquals(employeesUnderManager.get(0), (""));
    }

    /*@Test
    public void getAllEmployeesUnderManager() throws Exception {
        // Test Passed

        Employee manager = employeeService.getUserById(1);

        if (manager == null)
            throw new NotFoundException("cant find manager");

        //List<Employee> employeesUnderManager = new ArrayList<>(manager.());
        //List<EmployeeInfoOnlyDTO> employeesDTO = EmployeeInfoOnlyDTO.setEmployeeToDTOList(employeesUnderManager);

        ObjectMapper objectMapper = new ObjectMapper();
        ///String employeesUnderManagerJson = objectMapper.writeValueAsString(employeesDTO);


        mockMvc.perform(MockMvcRequestBuilders.get("/employee/manager/" + manager.getId()))
                .andExpect((ResultMatcher) content().json(employeesUnderManagerJson))
                .andExpect(status().isOk());
//        String managerEmployee = "Abbas";
//
//        String Query = "SELECT name, manager_name FROM employee WHERE manager_name = '"+managerEmployee+"'";
//
//        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(Query);
//        System.out.println("====================\n\n");
//
//        while(rs.next()){
//            String employeeManager = rs.getString("name");
//            System.out.println(employeeManager);
//            listAllEmployees(employeeManager);
//        }
//        System.out.println("====================\n\n");
    }*/

    @Test
    public void getEmployeesInTeam() throws Exception {
        String teamName = "team1";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(teamName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-in-team")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        List<String> employeesInTeam = userRepository.getEmployeesInTeam(teamName);
        assertEquals(employeesInTeam.get(0), ("7amada"));
    }

    @Test
    @DatabaseSetup("/getEmployeesUnderSpecificManager.xml")
    public void getEmployeesUnderSpecificManager() throws Exception {
        String managerName = "7amada";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(managerName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-under-specific-manager")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        List<String> employees = userRepository.getEmployeesUnderSpecificManager(managerName);
        assertEquals(employees.get(0), ("mohamed"));
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/expectedRemoveManager.xml")
    public void removeManager() throws Exception {
        Integer oldManagerId = 4;
        employeeService.removeManager(oldManagerId);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/expectedSalaryRaise.xml")
    public void salaryRaise() throws Exception {
        Integer employeeId = 4, raise = 2000;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/raise-salary/" + employeeId)
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raise)))
                .andExpect(status().isOk());

        double expectedSalary = userRepository.findById(employeeId).orElseGet(null).getGrossSalary();
        assertEquals(expectedSalary, 7000);
    }

}
