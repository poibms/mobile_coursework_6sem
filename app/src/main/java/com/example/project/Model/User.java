package com.example.project.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private int id;
    private String login;
    private String email;
    private String password;

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public User() { }

    //get
    public int getId() {return id;}
    public String getLogin() {return login;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}


    //set

    public void setLogin(String login) {this.login = login;}
    public void setEmail(String email) {this.login = email;}
    public void setPassword(String password) {this.login = password;}

}
