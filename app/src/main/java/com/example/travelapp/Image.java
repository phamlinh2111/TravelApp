package com.example.travelapp;

public class Image {
    private int idImage;
    private int idPlace;
    private String url;
    private String caption;

    // Constructor
    public Image(int idImage, int idPlace, String url, String caption) {
        this.idImage = idImage;
        this.idPlace = idPlace;
        this.url = url;
        this.caption = caption;
    }

    // Getters and Setters
    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

