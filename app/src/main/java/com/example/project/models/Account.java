package com.example.project.models;

public class Account {
    private Integer id;
    private String email;
    private String role;
    private String status;
    private String token;

    public Account(Integer id, String email, String role, String status, String token) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
        this.token = token;
    }

    public Integer getId() { return id; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getToken() { return token; }

    public void setId(Integer id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setStatus(String status) { this.status = status; }
    public void setRole(String role) { this.role = role; }
    public void setToken(String token) { this.token = token; }
}
