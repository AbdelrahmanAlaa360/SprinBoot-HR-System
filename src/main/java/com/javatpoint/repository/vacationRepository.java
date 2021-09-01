package com.javatpoint.repository;

import com.javatpoint.model.Vacations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface vacationRepository extends CrudRepository<Vacations, Integer> {

    //List<Vacations> findByEmployeeId(Integer employeeId);
    @Transactional
    @Query(value = "SELECT COUNT(*) FROM vacations WHERE employee_id = :employeeId AND exceeded = 1 AND year = :currentYear", nativeQuery = true)
    int countExceededVacations(Integer employeeId, int currentYear);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM vacations WHERE employee_id = :employeeId AND year = :currentYear", nativeQuery = true)
    int countCurrentYearVacations(Integer employeeId, int currentYear);

}
