package com.javatpoint.repository;

import com.javatpoint.model.SalaryHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SalaryHistoryRepository extends CrudRepository<SalaryHistory, Integer> {
    @Transactional
    @Query(value = "SELECT * FROM salary_history WHERE employee_id = :employeeId", nativeQuery = true)
    List<SalaryHistory> getSalaryHistories(Integer employeeId);

    @Query(value = "SELECT * FROM salary_history WHERE employee_id = :employeeId AND year = :year AND month = :month", nativeQuery = true)
    SalaryHistory getSalaryOfSpecificMonth(Integer employeeId, Integer month, Integer year);

}
