package com.javatpoint.springbootexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.ApacheDerbyExampleApplication;
import com.javatpoint.model.Department;
import com.javatpoint.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApacheDerbyExampleApplication.class)
public class DepartmentServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void addDepartment() throws Exception {
        Department department = new Department();
        department.setDepartment_name("Dept2");
        department.setDepartment_id(2);
        Department result =  departmentService.saveEmployee(department);
        assertThat(result).usingRecursiveComparison().isEqualTo(department);
//        assertEquals(result,department);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String body = objectMapper.writeValueAsString(department);
//        mockMvc.perform(MockMvcRequestBuilders.post("/HR/add-department")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(body))
//            .andExpect(status().isOk()).andDo(print());
    }

}
