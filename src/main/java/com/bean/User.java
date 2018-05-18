package com.bean;

public class User {
	
    int id;
    String password;
    String name;
    String email;
    String address;
    String number;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String pw) {
        this.password = pw;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String nm) {
        this.name = nm;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String em) {
        this.email = em;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String add) {
        this.address = add;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String num) {
        this.number = num;
    }

    
}
