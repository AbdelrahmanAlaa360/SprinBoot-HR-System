-- auto-generated definition
create table department
(
    department_name varchar(255) null,
    department_id   int          not null
        primary key
);

-- auto-generated definition
create table employee
(
    grad_date    int          not null,
    manager_name varchar(255) null,
    team_name    varchar(255) null,
    net_salary   int          not null,
    department   varchar(255) null,
    birth_date   int          not null,
    name         varchar(255) null,
    gross_salary int          not null,
    gender       varchar(255) null,
    experience   varchar(255) null,
    id           int auto_increment
        primary key
);

-- auto-generated definition
create table managers
(
    managerName varchar(255) null,
    id          int auto_increment
        primary key
);

-- auto-generated definition
create table vacations
(
    id            int auto_increment
        primary key,
    employee_name varchar(255) not null,
    year          int          not null,
    exceeded      tinyint      not null,
    employee_id   int          not null
);

-- auto-generated definition
create table salary_history
(
    id                int auto_increment
        primary key,
    employee_name     varchar(255) not null,
    tax_and_insurance int          not null,
    deductions        int          null,
    gross_salary      int          not null,
    net_salary        int          not null,
    employee_id       int          null,
    month             int          not null,
    year              int          not null,
    bonus             int          not null
);

-- auto-generated definition
create table users_account
(
    id        int          null,
    user_name varchar(255) null,
    password  varchar(255) null,
    role      varchar(255) null
);


