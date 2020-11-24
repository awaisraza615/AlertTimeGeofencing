package com.arenterprize;

public class User {
    String name, email,phone,country;

   public User(){

    }

    public String getname() {
        return name;
    }

    public void setname(String fullname) {
        this.name = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User(String fullname, String email, String phone, String country) {
        this.name = fullname;
        this.email = email;
        this.phone = phone;
        this.country = country;
    }
}
