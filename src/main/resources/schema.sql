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

