package com.arenterprize;

public class Post {

    String name;
    String date;
    String time;
    String logitude;
    String latitude;

    public Post() {
    }

    public Post(String name, String date, String time, String logitude, String latitude) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.logitude = logitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
