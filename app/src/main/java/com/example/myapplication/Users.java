package com.example.myapplication;

public class Users {
    String username;
    String firstname;
    String lastname;
    String password;
    String email;


    public Users(String f, String l, String u, String p, String e){
        firstname = f;
        lastname = l;
        username = u;
        password = p;
        email = e;


    }
    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getUsername() {
        return username;
    }
}
