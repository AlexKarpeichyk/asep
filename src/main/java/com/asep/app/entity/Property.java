package com.asep.app.entity;

public class Property extends BaseEntity {

    private double price;
    private String date;
    private String housetype;
    private boolean newProperty;
    private String duration;
    private String address;
    private double longitude;
    private double latitude;

    public Property() {

    }

    public Property(String propertyId, double price, String date, String housetype, boolean newProperty, String duration, String address, double longitude, double latitude) {
        super.id = propertyId;
        this.price = price;
        this.date = date;
        this.housetype = housetype;
        this.newProperty = newProperty;
        this.duration = duration;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHousetype() {
        return housetype;
    }

    public void setHousetype(String housetype) {
        this.housetype = housetype;
    }

    public boolean isNewProperty() {
        return newProperty;
    }

    public void setNewProperty(boolean newProperty) {
        this.newProperty = newProperty;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public static class PropertyBuilder {

        private String id;
        private double price;
        private String date;
        private String houseType;
        private boolean newProperty;
        private String duration;
        private String address;
        private double longitude;
        private double latitude;


        public PropertyBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public PropertyBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public PropertyBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public PropertyBuilder setHouseType(String houseType) {
            this.houseType = houseType;
            return this;
        }

        public PropertyBuilder setNewProperty(boolean newProperty) {
            this.newProperty = newProperty;
            return this;
        }

        public PropertyBuilder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public PropertyBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public PropertyBuilder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public PropertyBuilder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Property build() {
            return new Property(id, price, date, houseType, newProperty, duration, address, longitude, latitude);
        }

    }

}
