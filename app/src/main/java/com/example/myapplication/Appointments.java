package com.example.myapplication;

public class Appointments {

    public String vendorname;
    public String user;
    public String date;
    public String totalamount;
    public String servicename;
    public String clientp;
    public String status;

    public Appointments(String vendorname,String user, String date, String totalamount, String servicename,String clientp) {
        this.vendorname = vendorname;
        this.user=user;
        this.date = date;
        this.totalamount = totalamount;
        this.servicename = servicename;
        this.clientp = clientp;
        status = "0";
    }

    public Appointments(){

    }

    public String getStatus() {
        return status;
    }

    public String getVendorname() {
        return vendorname;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public String getServicename() {
        return servicename;
    }

    public String getClientp() {
        return clientp;
    }
}


