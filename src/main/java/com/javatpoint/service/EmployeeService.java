package com.javatpoint.service;

import com.javatpoint.model.Salary;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javatpoint.model.Employee;
import com.javatpoint.repository.UserRepository;

@Service
public class EmployeeService {
    @Autowired
    public UserRepository userRepository;

    public Employee getUserById(int id) throws NotFoundException {
        if (userRepository.existsById(id) == false)
            throw new NotFoundException("Not Found");
        return userRepository.getById(id);
    }

    public Employee addUser(Employee userRecord) {
        userRepository.save(userRecord);
        return userRecord;
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public boolean existsById(Integer id)  {
        return userRepository.existsById(id);
    }

    public Employee updateUser(Employee updatedEmployee, Employee originalEmployee) throws NotFoundException {
        Employee.updateEmployee(updatedEmployee, originalEmployee);
        return userRepository.save(originalEmployee);
    }

    public Salary getSalary(int id) throws NotFoundException{
        Employee employee = getUserById(id);
        return new Salary(employee);
    }



}