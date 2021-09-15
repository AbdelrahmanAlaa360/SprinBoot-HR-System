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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
public class MoreTestsOnEmployees {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UsersAccountRepository usersAccountRepository;


    @Test
    public void testWrongNotAuthenticatedAddUser() throws Exception {
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testHrAddUser() throws Exception {
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
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }


    @Test
    public void testWrongPasswordAddUser() throws Exception {
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
                        .with(httpBasic("admin", "wrongpassword"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUnauthorizedForbiddenUser() throws Exception {
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
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testNegativeNetSalary() throws Exception {
        Date dt = new Date();
        int year = dt.getYear() + 1900;

        Employee userRecord = new Employee();
        userRecord.setName("Mohammed");
        userRecord.setBirthDate(1988);
        userRecord.setDepartment("CS");
        userRecord.setExperience("Senior");
        userRecord.setGender("Male");
        userRecord.setTeamName("Team 3");
        userRecord.setGrossSalary(-5000);
        userRecord.setManagerName("fathi");
        userRecord.setGradDate(2019);
        userRecord.setJoinYear(year);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(userRecord);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/add-user")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void testNegativeNetSalaryWithHr() throws Exception {
        Date dt = new Date();
        int year = dt.getYear() + 1900;

        Employee userRecord = new Employee();
        userRecord.setName("Mohammed");
        userRecord.setBirthDate(1988);
        userRecord.setDepartment("CS");
        userRecord.setExperience("Senior");
        userRecord.setGender("Male");
        userRecord.setTeamName("Team 3");
        userRecord.setGrossSalary(-5000);
        userRecord.setManagerName("fathi");
        userRecord.setGradDate(2019);
        userRecord.setJoinYear(year);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(userRecord);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/add-user")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void deleteNotFoundEmployee() throws Exception {
        int id = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/delete-user")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound()).andDo(print());
    }


    @Test
    public void deleteNotFoundEmployeeWithoutAuthentication() throws Exception {
        int id = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/delete-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    public void deleteNotFoundEmployeeWithHr() throws Exception {
        int id = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/delete-user")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void getInfoOfNonExistedEmployee() throws Exception {
        int employeeId = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employee/" + employeeId)
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void getInfoOfNonExistedEmployeeWithHr() throws Exception {
        int employeeId = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employee/" + employeeId)
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void getInfoOfNonExistedEmployeeWitoutAuthentication() throws Exception {
        int employeeId = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employee/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @Transactional
    public void updateNotExistedEmployeeWithHr() throws Exception {
        Integer employeeId = 20;
        Employee employee = new Employee();
        employee.setGrossSalary(10000);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/update-user/" + employeeId)
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotExistedEmployee() throws Exception {
        Integer employeeId = 20;
        Employee employee = new Employee();
        employee.setGrossSalary(10000);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/update-user/" + employeeId)
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void updateNotExistedEmployeeWithoutAuthentication() throws Exception {
        Integer employeeId = 20;
        Employee employee = new Employee();
        employee.setGrossSalary(10000);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/update-user/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void getEmployeeSalaryWithoutAuthentication() throws Exception {
        int id = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(id);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-salary/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void getSalaryOfNotExistedEmployee() throws Exception {
        int id = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(id);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-salary/" + id)
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getSalaryOfNotExistedEmployeeWithHr() throws Exception {
        int id = 20;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(id);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-salary/" + id)
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEmployeesInNotExistingTeam() throws Exception {
        String teamName = "Team 404";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(teamName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-in-team")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeesInNotExistingTeamWithHr() throws Exception {
        String teamName = "Team 404";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(teamName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-in-team")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeesInTeamWithoutAuthentication() throws Exception {
        String teamName = "team1";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(teamName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-in-team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getEmployeesUnderManager() throws Exception {
        String managerName = "ahmed";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(managerName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-under-specific-manager")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeesUnderManagerWithHr() throws Exception {
        String managerName = "ahmed";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(managerName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-under-specific-manager")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeesUnderManagerWithoutAuth() throws Exception {
        String managerName = "ahmed";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(managerName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-under-specific-manager")
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getEmployeesUnderManagerWrongPassword() throws Exception {
        String managerName = "ahmed";
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(managerName);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/get-employees-under-specific-manager")
                        .with(httpBasic("admin", "admin332211"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void salaryRaiseNotExisitingEmployee() throws Exception {
        Integer employeeId = 40, raise = 2000;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/raise-salary/" + employeeId)
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raise)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void salaryRaiseByHrNotExisitingEmployee() throws Exception {
        Integer employeeId = 40, raise = 2000;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/raise-salary/" + employeeId)
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raise)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void salaryRaiseWrongPasswordAuth() throws Exception {
        Integer employeeId = 4, raise = 2000;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/raise-salary/" + employeeId)
                        .with(httpBasic("admin", "hr"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raise)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void salaryRaiseNotAuthenticated() throws Exception {
        Integer employeeId = 4, raise = 2000;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/raise-salary/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raise)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void salaryRaiseUnAuthorizedUser() throws Exception {
        Integer employeeId = 4, raise = 2000;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.put("/HR/raise-salary/" + employeeId)
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raise)))
                .andExpect(status().isForbidden());
    }
}
