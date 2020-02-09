package com.example.myapplication;

public class Request {
    public String fullname;
    public String email;
    public String contact;
    public String address;
    public String service;
    public String problem;
    public String userid;

    public Request() {

    }

    public Request(String fullname, String email,String contact, String address, String service, String problem,String userid) {
        this.fullname=fullname;
        this.email=email;
        this.contact=contact;
        this.address=address;
        this.service=service;
        this.problem=problem;
        this.userid=userid;
    }

    public String getUserid() {
        return userid;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getService() {
        return service;
    }

    public String getProblem() {
        return problem;
    }
}