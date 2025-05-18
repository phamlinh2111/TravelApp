package com.example.travelapp;

public class Province {
    private int id;
    private String name;

    public Province(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setIdProvince(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

