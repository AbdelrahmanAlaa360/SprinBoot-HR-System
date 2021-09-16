package com.javatpoint.springbootexample;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.javatpoint.model.Vacations;
import com.javatpoint.repository.vacationRepository;
import com.javatpoint.service.VacationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DatabaseSetup("/data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class MoreTestsOnVacation {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    VacationService vacationService;
    @Autowired
    vacationRepository vacationRepository;

    @Test
    public void addVacationUnAuthenticated() throws Exception {
        Integer employeeId = 4;
        Vacations vacations = new Vacations();
        vacations.setEmployee_name("7amada");
        vacations.setEmployeeId(employeeId);
        vacations.setYear(2021);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vacations);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/vacations/add-vacation/")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addVacationWrongPassword() throws Exception {
        Integer employeeId = 4;
        Vacations vacations = new Vacations();
        vacations.setEmployee_name("7amada");
        vacations.setEmployeeId(employeeId);
        vacations.setYear(2021);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vacations);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/vacations/add-vacation/")
                        .with(httpBasic("admin", "aadd"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addVacationWrongHrPassword() throws Exception {
        Integer employeeId = 4;
        Vacations vacations = new Vacations();
        vacations.setEmployee_name("7amada");
        vacations.setEmployeeId(employeeId);
        vacations.setYear(2021);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vacations);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/vacations/add-vacation/")
                        .with(httpBasic("hr", "hrhr"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addVacationUnAuthorized() throws Exception {
        Integer employeeId = 4;
        Vacations vacations = new Vacations();
        vacations.setEmployee_name("7amada");
        vacations.setEmployeeId(employeeId);
        vacations.setYear(2021);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vacations);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/vacations/add-vacation/")
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addVacationForNotExistingEmployee() throws Exception {
        Integer employeeId = 40;
        Vacations vacations = new Vacations();
        vacations.setEmployee_name("7amada");
        vacations.setEmployeeId(employeeId);
        vacations.setYear(2021);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vacations);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/vacations/add-vacation/")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addVacationWithHr() throws Exception {
        Integer employeeId = 4;
        Vacations vacations = new Vacations();
        vacations.setEmployee_name("7amada");
        vacations.setEmployeeId(employeeId);
        vacations.setYear(2021);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vacations);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/vacations/add-vacation/")
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void getExceededVacationsUnAuthenticated() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/vacations/get-vacation/")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getExceededVacationsWrongPassword() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/vacations/get-vacation/")
                        .with(httpBasic("admin", "aadd"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void getExceededVacationsWrongHrPassword() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/vacations/get-vacation/")
                        .with(httpBasic("hr", "hrhr"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void getExceededVacationsNotExistingEmployee() throws Exception {
        Integer employeeId = 40;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/vacations/get-vacation/")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getExceededVacationsWithHr() throws Exception {
        Integer employeeId = 4;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/vacations/get-vacation/" + employeeId)
                        .with(httpBasic("hr", "hr123"))
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

}
