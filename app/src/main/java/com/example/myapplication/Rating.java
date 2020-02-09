package com.example.myapplication;

public class Rating {

    public String userkey;
    public String servicereceived;
    public String ratevalue;
    public String comment;

    public Rating(){

    }

    public Rating(String userkey, String servicereceived, String ratevalue, String comment) {
        this.userkey = userkey;
        this.servicereceived = servicereceived;
        this.ratevalue = ratevalue;
        this.comment = comment;
    }

    public String getUserkey() {
        return userkey;
    }

    public String getServicereceived() {
        return servicereceived;
    }

    public String getRatevalue() {
        return ratevalue;
    }

    public String getComment() {
        return comment;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public void setServicereceived(String servicereceived) {
        this.servicereceived = servicereceived;
    }

    public void setRatevalue(String ratevalue) {
        this.ratevalue = ratevalue;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
