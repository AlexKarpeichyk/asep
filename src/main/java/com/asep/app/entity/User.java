package com.asep.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@SuppressWarnings("serial")
@Document(collection = "user")
public class User extends BaseEntity {

    private String country;
    private String city;
    private List<Location> location;
    private String name;
    private String password;
    private Device device;

    public User() {

    }

    public User(String country, String city, List<Location> location, String name, Device device, String password, String email) {
        this.country = country;
        this.city = city;
        this.location = location;
        this.name = name;
        this.device = device;
        this.password = password;
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

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
