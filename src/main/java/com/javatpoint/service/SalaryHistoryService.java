package com.javatpoint.service;

import com.javatpoint.Exceptions.EmployeeNotFoundException;
import com.javatpoint.model.Employee;
import com.javatpoint.model.SalaryHistory;
import com.javatpoint.repository.SalaryHistoryRepository;
import com.javatpoint.repository.UserRepository;
import com.javatpoint.repository.vacationRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalaryHistoryService {
    @Autowired
    public SalaryHistoryRepository salaryHistoryRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public vacationRepository vacationRepository;
    @Autowired
    public EmployeeService employeeService;

    public double vacationsDeducationCalculation(int employeeId) {
        Date dt = new Date();
        final int currentYear = dt.getYear() + 1900;
        int exceededDays = vacationRepository.countExceededVacations(employeeId, currentYear);
        Employee employee = userRepository.getById(employeeId);
        final double grossSalaryPerDay = employee.getGrossSalary() / 30;
        final double vacationDeductions = exceededDays * grossSalaryPerDay;
        vacationRepository.deductVacationsFromSalary(employeeId, currentYear);
        return vacationDeductions;
    }

    public SalaryHistory setSalaryHistory(String employeeName, double taxInsuranceRate, double vacationDeductions, double bonus, double grossSalary, double netSalary, int month, int currentYear, int employeeId) {
        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setEmployee_name(employeeName);
        salaryHistory.setTax_and_insurance(taxInsuranceRate);
        salaryHistory.setDeductions(vacationDeductions);
        salaryHistory.setBonus(bonus);
        salaryHistory.setGross_salary(grossSalary);
        salaryHistory.setNet_salary(netSalary);
        salaryHistory.setMonth(month);
        salaryHistory.setYear(currentYear);
        salaryHistory.setEmployee_id(employeeId);
        return salaryHistory;
    }

    public SalaryHistory addSalary(int employeeId) {
        if(!employeeService.existsById(employeeId)){
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        Employee employee = userRepository.getById(employeeId);
        String employeeName = employee.getName();
        Date dt = new Date();
        double vacationDeductions = vacationsDeducationCalculation(employeeId);
        final double bonus = 1000;
        double grossSalary = employee.getGrossSalary();
        final double taxInsuranceRate = 0.15 * grossSalary;
        final int month = dt.getMonth() + 1;
        final int currentYear = dt.getYear() + 1900;

        double netSalary = grossSalary;
        netSalary += bonus;
        netSalary -= taxInsuranceRate;
        netSalary -= vacationDeductions;

        SalaryHistory salaryHistory = setSalaryHistory(employeeName, taxInsuranceRate, vacationDeductions, bonus, grossSalary, netSalary, month, currentYear, employeeId);
        salaryHistoryRepository.save(salaryHistory);
        return salaryHistory;
    }

    public List<SalaryHistory> getSalaryHistories(Integer employeeId) {
        List<SalaryHistory> salaryHistory = salaryHistoryRepository.getSalaryHistories(employeeId);
        for (SalaryHistory salaryHistory1 : salaryHistory) {
            System.out.println(
                    "Id: " +salaryHistory1.getEmployee_id() + " | Name: " +
                            salaryHistory1.getEmployee_name() + " | Month: " +
                            salaryHistory1.getMonth() + " | Year: " +
                            salaryHistory1.getYear() + " | Bonus: " +
                            salaryHistory1.getBonus() + " | Tax & Insurance: " +
                            salaryHistory1.getTax_and_insurance() + "\nDeductions: " +
                            salaryHistory1.getDeductions() + " | Gross Salary: " +
                            salaryHistory1.getGross_salary() + " | Net Salary:" +
                            salaryHistory1.getNet_salary() + "\n======"
            );
        }
        return salaryHistory;
    }
    public SalaryHistory getSalaryOfSpecificMonth(Integer month, Integer year, Integer employeeId){
        if(!userRepository.existsById(employeeId)){
            throw new EmployeeNotFoundException("Employee Not Found\nPlease Enter Corret Employee Id");
        }
        return salaryHistoryRepository.getSalaryOfSpecificMonth(employeeId, month, year);
    }
}