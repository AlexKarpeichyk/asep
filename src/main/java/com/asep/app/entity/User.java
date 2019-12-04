package com.asep.app.entity;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@SuppressWarnings("serial")
@Document(collection = "user")
@Builder
public class User extends BaseEntity {

    private String country;
    private String city;
    private List<Location> location;
    private String name;
    private String password;
    private Device device;

    public User() {

    }

    public User(String id, String country, String city, List<Location> location, String name, Device device, String password) {
        super.id = id;
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


    public static class UserBuilder {
        private String id;
        private String country;
        private String city;
        private List<Location> location;
        private String name;
        private String password;
        private Device device;

        public UserBuilder() {
        }

        public UserBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public UserBuilder setLocation(List<Location> loc) {
            this.location = loc;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setDevice(Device device) {
            this.device = device;
            return this;
        }

        public User build() {
            return new User(id, country, city, location, name, device, password);
        }
    }
}
