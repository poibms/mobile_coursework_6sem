package com.example.project.Model;

import java.io.Serializable;

public class Trip implements Serializable {
    private int id;
    private String start, finish, startDate, startTime, transportType;
    private int capacity, price;

    public Trip(String start, String finish, String startDate, String startTime,
                String transportType, int capacity, int price) {
        this.start = start;
        this.finish = finish;
        this.startDate = startDate;
        this.startTime = startTime;
        this.transportType = transportType;
        this.capacity = capacity;
        this.price = price;
    }
    public Trip() { }

    //get
    public int getId() {return id;}
    public String getStart() {return start;}
    public String getFinish() {return finish;}
    public String getStartDate() {return startDate;}
    public String getStartTime() {return startTime;}
    public String getTransportType() {return transportType;}
    public int getCapacity() {return capacity;}
    public int getPrice() {return price;}

    //set
    public void setId(int id) {this.id = id;}
    public void setStart(String start) {this.start = start;}
    public void setFinish(String finish) {this.finish = finish;}
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public void setStartTime(String startTime) {this.startTime = startTime;}
    public void setTransportType(String transportType) {this.transportType = transportType;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    public void setPrice(int price) {this.price = price;}

}
