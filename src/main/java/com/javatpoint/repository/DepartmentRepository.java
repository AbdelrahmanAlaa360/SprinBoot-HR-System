package com.javatpoint.repository;

import com.javatpoint.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    //@Query(value = "SELECT name FROM department WHERE department_name")
}
