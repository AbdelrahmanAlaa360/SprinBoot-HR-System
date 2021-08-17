package com.javatpoint.springbootexample;

import com.javatpoint.model.Employee;
import com.javatpoint.service.EmployeeService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void addEmployee() {
        Employee userRecord  = new Employee();
        userRecord.setName("ahmed");
        userRecord.setBirthDate(1900);
        userRecord.setDepartment("IS");
        userRecord.setExperience("LOW");
        userRecord.setGender("Male");
        userRecord.setTeamName("Team 10");
        userRecord.setGrossSalary(8000);
        userRecord.setNetSalary(7500);
        userRecord.setManagerName("Ahmed");
        userRecord.setGradDate(2015);
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
    public void updateEmployee() throws NotFoundException {
        int id = 1;
        Employee employer = employeeService.getUserById(id);
        employer.setName("abdo");
        employer.setDepartment("Dept");
        String query = String.valueOf(employer.getId());
        Employee update = employeeService.updateUser(employer, query);
        assertThat(update).usingRecursiveComparison().isEqualTo(employer);
    }



}
