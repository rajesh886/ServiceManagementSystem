package com.example.myapplication;

public class Bid {
    private String amount;
    private String days;
    private String vendorid;
    private String uid;
    private String email;
    private String problem;
    private String service;

    public Bid(){

    }

    public Bid(String amount, String days, String vendorid, String uid, String email, String problem, String service) {
        this.amount = amount;
        this.days = days;
        this.vendorid = vendorid;
        this.uid = uid;
        this.email = email;
        this.problem = problem;
        this.service = service;
    }

    public String getAmount() {
        return amount;
    }

    public String getDays() {
        return days;
    }

    public String getVendorid() {
        return vendorid;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getProblem() {
        return problem;
    }

    public String getService() {
        return service;
    }
}
