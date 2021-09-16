package com.javatpoint.springbootexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.javatpoint.model.SalaryHistory;
import com.javatpoint.repository.SalaryHistoryRepository;
import com.javatpoint.service.SalaryHistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DatabaseSetup("/data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class SalaryHistoryServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SalaryHistoryService salaryHistoryService;
    @Autowired
    SalaryHistoryRepository salaryHistoryRepository;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/expectedAddSalaryHistory.xml")
    public void addSalaryHistory() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/salary-history/add/" + employeeId)
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/data.xml")
    public void getSalaryHistories() throws Exception{
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/salary-history/get-salary/" + employeeId)
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/data.xml")
    public void getSpecificMonthSalary() throws Exception{
        Integer employeeId = 4, month = 9, year = 2021;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/salary-history/get-specific-month-salary/" + employeeId + "/" + month + "/" + year)
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

}
