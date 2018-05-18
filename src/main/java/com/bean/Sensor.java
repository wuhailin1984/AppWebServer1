package com.bean;

public class Sensor {
    public int id;
    public String name;
    public double temperature;
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public double getTemperature() {
        return temperature;
    }
    public void setHp(double tmp) {
        this.temperature = tmp;
    }

     
}