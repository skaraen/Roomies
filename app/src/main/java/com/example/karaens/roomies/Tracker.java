package com.example.karaens.roomies;

public class Tracker {
    String lat,lng,email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Tracker(String lat, String lng, String email) {
        this.lat = lat;
        this.lng = lng;
        this.email = email;
    }

    public Tracker() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
