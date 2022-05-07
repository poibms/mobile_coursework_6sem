package com.example.project.Model;

public class OrderedTickets {
    private int userId, tripId, countPlaces, price;

    public OrderedTickets(int userId, int tripId, int countPlaces, int price) {
        this.userId = userId;
        this.tripId = tripId;
        this.countPlaces = countPlaces;
        this.price = price;
    }

    public OrderedTickets () {}

    //get
    public int getUserId() {return userId;}
    public int getTripId() {return tripId;}
    public int getCountPlaces() {return countPlaces;}
    public int getPrice() {return price;}

    //set
    public void setUserId(int userId) {this.userId = userId;}
    public void setTripId(int tripId) {this.tripId = tripId;}
    public void setCountPlaces(int countPlaces) {this.countPlaces = countPlaces;}
}
