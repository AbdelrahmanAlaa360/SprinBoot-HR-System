package com.javatpoint.service;

import com.javatpoint.model.Department;
import com.javatpoint.repository.DepartmentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    public DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {
//        if(departmentRepository.existsById(department.getDepartment_id())){
//            throw new NotFoundException("Deprtment Already Exists");
//        }
        departmentRepository.save(department);
        return department;
    }
}
