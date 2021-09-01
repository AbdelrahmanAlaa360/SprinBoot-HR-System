package com.javatpoint.repository;

import com.javatpoint.model.Employee;
import com.javatpoint.model.Vacations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface vacationRepository extends CrudRepository<Vacations, Integer> {

    //List<Vacations> findByEmployeeId(Integer employeeId);
}
