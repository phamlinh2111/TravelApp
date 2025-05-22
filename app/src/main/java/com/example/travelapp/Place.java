package com.example.travelapp;

public class Place {
    private int idPlace;
    private int idProvince;
    private String name;
    private double rate;
    private String description;
    private int ticketPrice;
    private String time;
    private String phone;
    private String type;
    private double latitude;
    private double longitude;

    public Place(int idPlace, int idProvince, String name, double rate, String description, int ticketPrice,
                 String time, String phone, String type, double latitude, double longitude) {
        this.idPlace = idPlace;
        this.idProvince = idProvince;
        this.name = name;
        this.rate = rate;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.time = time;
        this.phone = phone;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter methods
    public int getIdPlace() {
        return idPlace;
    }

    public int getIdProvince() {
        return idProvince;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public String getTime() {
        return time;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Setter methods
    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
