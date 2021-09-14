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

//    @Query(value = "with recursive cte (id, birth_date, department, experience, gender, grad_date, gross_salary, manager_name, name, net_salary, team_name, join_year) as (\n" +
//            "    select     e1.id, e1.birth_date, e1.department, e1.experience, e1.gender, e1.grad_date, e1.gross_salary, e1.manager_name, e1.net_salary, e1.team_name, e1.join_year\n" +
//            "    from       employee e1\n" +
//            "    where      manager_name = :employeeId\n" +
//            "    union all\n" +
//            "    select     e.id, e.birth_date, e.department, e.experience, e.gender, e.grad_date, e.gross_salary, e.manager_name, e.net_salary, e.team_name, e.join_year\n" +
//            "    from       employee e\n" +
//            "                   inner join cte\n" +
//            "                              on e.manager_name = cte.id\n" +
//            ")\n" +
//            "select * from cte;", nativeQuery = true)
//    List<Employee> getAllEmployees(@Param("employeeId") Integer employeeId);

    @Query(
            value = "with recursive cte as ( \n" +
                    "            select  " + "id\n" + ",grad_date\n" + ",manager_name\n" + ",team_name\n" + ",net_salary\n" + ",department\n" + ",birth_date\n" + ",name\n" + ",gross_salary\n" + ",gender\n" + ",experience" + "\t\t\n" +
                    "            from       employee\n" +
                    "            where id = :employeeId" +
                    "            union all\n" +
                    "            select  p.id  ,p.grad_date ,p.manager_name,p.team_name,p.net_salary ,p.department,p.birth_date,p.name,p.gross_salary,p.gender,p.experience\n" +
                    "            from       employee p\n" +
                    "            inner join cte\n" +
                    "            on p.id = cte.id\n" +
                    "            )\n" +
                    "            select * from cte"
            , nativeQuery = true)
    List<Employee> getAllEmployees(@Param("employeeId") Integer employeeId);

    @Query(value = "SELECT name FROM employee WHERE team_name = :teamName", nativeQuery = true)
    List<String> getEmployeesInTeam(String teamName);

    @Query(value = "SELECT join_year FROM employee WHERE id = :employeeId", nativeQuery = true)
    int getJoinYear(int employeeId);

    @Query(value = "SELECT name FROM employee WHERE id = :employeeId", nativeQuery = true)
    String getEmployeeByName(int employeeId);

    @Query(value = "SELECT * FROM employee WHERE id = :employeeId", nativeQuery = true)
    Employee getEmployee(int employeeId);

    @Query(value = "SELECT name FROM employee WHERE manager_name = :managaerName ", nativeQuery = true)
    List<String> getEmployeesUnderSpecificManager(String managaerName);

    @Query(value = "SELECT net_salary FROM employee WHERE id = :employeeId", nativeQuery = true)
    Integer getNetSalary(int employeeId);

    @Query(value = "SELECT name FROM employee WHERE id = :oldManagerId", nativeQuery = true)
    String getManagerName(int oldManagerId);

}