package com.asep.spring.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document(collection = "user")
public class User extends BaseEntity {

    private String country;
    private String city;
    private String latitude;
    private String longitude;
    private String name;
    private Device device;

    public User() {

    }

    public User(String country, String city, String latitude, String longitude, String name, Device device) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.device = device;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
