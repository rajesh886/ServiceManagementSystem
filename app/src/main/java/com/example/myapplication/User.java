package com.example.myapplication;

public class User {
    public String email;
    public String sec1;
    public String sec2;
    public String service;
    public boolean IsVendor;

    public User() {

    }

    public User(String email,String sec1, String sec2) {
        this.email = email;
        this.sec1 = sec1;
        this.sec2 = sec2;
        IsVendor = false;
    }

    public User(String email,String service){
        this.email=email;
        this.service=service;
        IsVendor=true;
    }

    public String getEmail() {
        return email;
    }

    public String getSec1() {
        return sec1;
    }

    public String getSec2() {
        return sec2;
    }

    public String getService() {
        return service;
    }

    public boolean isVendor() {
        return IsVendor;
    }
}
