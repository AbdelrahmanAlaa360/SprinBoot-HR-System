package com.javatpoint.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.javatpoint.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Employee, Integer> {


    //User findByUsername(String username);

    @Query(
            value = "with recursive cte as ( \n" +
                    "            select  " + "id\n" + ",grad_date\n" + ",manager_name\n" + ",team_name\n" + ",net_salary\n" + ",department\n" + ",birth_date\n" + ",name\n" + ",gross_salary\n" + ",gender\n" + ",experience" + "\t\t\n" +
                    "            from       employee\n" +
                    "            where id = :employeeId" +
                    "            union all\n" +
                    "            select  p.id  ,p.grad_date ,p.manager_name,p.team_name,p.net_salary + ,p.department,p.birth_date,p.name,p.gross_salary,p.gender,p.experience\n" +
                    "            from       employee p\n" +
                    "            inner join cte\n" +
                    "            on p.employeeId = cte.employee_id\n" +
                    "            )\n" +
                    "            select * from cte; "
            , nativeQuery = true)
    List<Employee> getAllEmployees(@Param("employeeId") int employeeId);


    @Query(value = "SELECT join_year FROM employee WHERE id = :employeeId", nativeQuery = true)
    int getJoinYear(int employeeId);

    @Query(value = "SELECT name FROM employee WHERE id = :employeeId", nativeQuery = true)
    String getEmployeeByName(int employeeId);

    @Query(value = "SELECT * FROM employee WHERE id = :employeeId", nativeQuery = true)
    Employee getEmployee(int employeeId);
}