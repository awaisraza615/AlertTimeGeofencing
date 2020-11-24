package com.arenterprize;

public class TestItem {

    String rmname, rmdate, rmtime,logitude,latitude;



    public TestItem(String rmname, String rmdate, String rmtime, String logitude, String latitude) {
        this.rmname = rmname;
        this.rmdate = rmdate;
        this.rmtime = rmtime;
        this.logitude = logitude;
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getRmname() {
        return rmname;
    }

    public void setRmname(String rmname) {
        this.rmname = rmname;
    }

    public String getRmdate() {
        return rmdate;
    }

    public void setRmdate(String rmdate) {
        this.rmdate = rmdate;
    }

    public String getRmtime() {
        return rmtime;
    }

    public void setRmtime(String rmtime) {
        this.rmtime = rmtime;
    }
}
