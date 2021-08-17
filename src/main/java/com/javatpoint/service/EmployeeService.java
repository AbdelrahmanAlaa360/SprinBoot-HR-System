package com.javatpoint.service;

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

    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    public Employee updateUser(Employee employer, String id) throws NotFoundException {
        if(existsById(Integer.parseInt(id))){
            //employer.setId(Integer.parseInt(id));
                return userRepository.save(employer);
        }
        else {
            throw new NotFoundException("Not Found");
        }
    }

    public Employee updateEmployee(Employee oldEmployer, Employee newEmployer) {
        if(newEmployer.name != null)oldEmployer.name = newEmployer.name;
        if(newEmployer.gender != null)oldEmployer.gender = newEmployer.gender;
        if(newEmployer.birthDate != 0)oldEmployer.birthDate = newEmployer.birthDate;
        if(newEmployer.gradDate != 0)oldEmployer.gradDate = newEmployer.gradDate;
        if(newEmployer.experience != null)oldEmployer.experience = newEmployer.experience;
        if(newEmployer.department != null)oldEmployer.department = newEmployer.department;
        if(newEmployer.grossSalary != 0)oldEmployer.grossSalary = newEmployer.grossSalary;
        if(newEmployer.netSalary != 0)oldEmployer.netSalary = newEmployer.netSalary;
        if(newEmployer.teamName != null)oldEmployer.teamName = newEmployer.teamName;
        if(newEmployer.managerName != null)oldEmployer.managerName = newEmployer.managerName;
        return oldEmployer;
    }

}