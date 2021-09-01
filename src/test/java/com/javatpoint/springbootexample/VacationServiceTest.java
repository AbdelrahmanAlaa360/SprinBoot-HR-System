package com.javatpoint.springbootexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.model.Vacations;
import com.javatpoint.service.EmployeeService;
import com.javatpoint.service.VacationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.javatpoint.repository.vacationRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VacationServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    VacationService vacationService;
    @Autowired
    vacationRepository vacationRepository;


    @Test
    public void vacations() throws Exception {
        Integer employeeId = 4;
        Vacations vacations = new Vacations();
        vacations.setEmployee_name("7amada");
        vacations.setEmployeeId(employeeId);
        vacations.setYear(2021);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vacations);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/vacations/add-vacation/")
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());

        //assertEquals(id, vacationRepository.findById(id));
    }

    @Test
    public void getVacation() throws Exception {
        Integer employeeId = 4;

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.get("/HR/vacations/get-vacation/")
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
        //assertEquals(employeeId,vacationRepository.findById(employeeId));
    }
}
