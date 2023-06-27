package com.example.reminderapp;

public class Admins {
    String username,email,password,confirmpassword,usertype;
    public Admins(String username, String email, String password, String confirmpassword, String usertype){
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.usertype = usertype;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getConfirmpassword() {
        return confirmpassword;
    }
}
