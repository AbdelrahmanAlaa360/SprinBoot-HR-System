package com.javatpoint.springbootexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.javatpoint.repository.UserRepository;
import com.javatpoint.repository.UsersAccountRepository;
import com.javatpoint.service.EmployeeService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DatabaseSetup("/data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class MoreTestsOnSalaryHistory {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UsersAccountRepository usersAccountRepository;

    @Test
    public void addSalaryHistoryNotAuthenticated() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/salary-history/add/")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addSalaryHistoryWrongPassword() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/salary-history/add/")
                        .with(httpBasic("admin", "asgjk"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addSalaryHistoryNonAuthorized() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/salary-history/add/")
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addSalaryHistoryWithHr() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/salary-history/add/")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void addSalaryHistoryNotExistedUser() throws Exception {
        Integer employeeId = 40;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/salary-history/add/")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSalaryHistoriesNotAuthenticated() throws Exception{
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/salary-history/get-salary/")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getSalaryHistoriesWrongPassword() throws Exception{
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/salary-history/get-salary/")
                        .with(httpBasic("admin", "adminadmin"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getSalaryHistoriesWithHr() throws Exception{
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/salary-history/get-salary/")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }


    public void getSalaryHistoriesWithUser() throws Exception{
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/salary-history/get-salary")
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void getSalaryHistoriesNotExistedUser() throws Exception{
        Integer employeeId = 40;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/salary-history/get-salary")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }
}
