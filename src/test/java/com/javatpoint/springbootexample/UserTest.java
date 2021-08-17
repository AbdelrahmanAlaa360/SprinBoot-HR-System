package com.javatpoint.springbootexample;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.model.Employee;
import com.javatpoint.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService userService;

    @Test
    public void addUser() throws Exception{
        Employee userRecord = new Employee();
        userRecord.setName("ahmed");
        userRecord.setBirthDate(2000);
        userRecord.setDepartment("Software");
        userRecord.setExperience("High");
        userRecord.setGender("Male");
        userRecord.setTeamName("Team 1");
        userRecord.setGrossSalary(5000);
        userRecord.setNetSalary(4900);
        userRecord.setManagerName("Ahmed");
        userRecord.setGradDate(54);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(userRecord);

        mockMvc.perform(MockMvcRequestBuilders.post("/HR/add-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk()).andDo(print());
    }

}
