package com.javatpoint.springbootexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.javatpoint.ApacheDerbyExampleApplication;
import com.javatpoint.model.Department;
import com.javatpoint.model.Employee;
import com.javatpoint.repository.DepartmentRepository;
import com.javatpoint.service.DepartmentService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApacheDerbyExampleApplication.class)
@DatabaseSetup("/data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class DepartmentServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/expectedAddDepartment.xml")
    public void addDepartment() throws Exception {
        Department department = new Department();
        department.setDepartment_name("Dept1");

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(department);
        mockMvc.perform(MockMvcRequestBuilders.post("/HR/add-department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());
    }

}
