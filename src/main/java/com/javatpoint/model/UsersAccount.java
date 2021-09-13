package com.javatpoint.model;

import javax.persistence.*;

@Entity
public class UsersAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String user_name;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false)
    private String role;

    public UsersAccount(Integer id, String user_name, String password, String role) {
        this.id = id;
        this.user_name = user_name;
        this.password = password;
        this.role = role;
    }

    public UsersAccount() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
