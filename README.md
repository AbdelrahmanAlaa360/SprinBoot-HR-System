# Spring Boot HR Management System

#### The system is developed using [JavaSpringBoot](https://spring.io/projects/spring-boot) Maven using [MVC Pattern](https://www.freecodecamp.org/news/the-model-view-controller-pattern-mvc-architecture-and-frameworks-explained/) and running on MySQL database, and unit testing with H2 database. The main objectice of the system is manage different employees and record their activities in their company and managing departments, teams, salaries, and annual vacations.
#### Also the system is covering most of scenarios using DB UnitTesting with more than 65 tests, also SpringBoot security is enabled for handling requests and Basic Authenticaition is required, and tested with Postman Collection

## Description

- The system is able to add new employee, modify it, and delete the employee
- Each employee belongs to a team 
- The employee has a gross salary which is the salary before deduction
- The salary paid to the employee is called net salary, it is equal to the gross salary deducted from the tax ratio (fixed 15% on all employees) and the fixed insurance amount
- Removing a manager will move all his subordinates to his manager
- The employee has first name, last name, national Id, birthdate, graduation date, degree (fresh, intermediate, senioir, Architect), years of experience, his manager, team, and the gross salary
- All employees have 21 daya leaves except those who worked for 10 years or more they have 30 days
- Every employee record his leaves and if they exceeded the allowed days of leaves this will be deducted from the their gross salary
- An employee can receive a permenant raises for his salary
- An employee can receive a bonus for a single month
- For each employee they can ask for 
  - Their salary history for each month since he joined
  - Show each salary he received (deductions, excceded leaves, taxes, insurance, raises ,bonuses)
- SpringBoot security for handling requests
- Flyway database
  
 ## REST APIs
- [ ] Rest API to add employee
- [ ] Rest API to modify employee
- [ ] Rest API to delete employee
- [ ] Rest API to get employee Info
- [ ] Rest API to get Employee Salary info (net and gross)
- [ ] Rest API to get All employees under specific manager recursivly
- [ ] Rest API to get All Employees in some team
- [ ] Rest API to get employees directly under specific manager
- [ ] Rest API to get salary history for each employee includeing his deductions, bonuses, and tax rate
- [ ] Rest API to add vacation and determine whether this vacation has exceeded the limit or not
- [ ] Rest API to get exceeded vacations for each employee
