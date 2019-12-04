package com.asep.app.entity;

public class RadiusRequest {
    private double radius;
    private double longitude;
    private double latitude;

    public RadiusRequest() {
    }

    public RadiusRequest(double radius, double longitude, double latitude) {
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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

    public static class RadiusRequestBuilder {
        private double radius;
        private double longitude;
        private double latitude;

        public RadiusRequestBuilder() {

        }

        public RadiusRequestBuilder setRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public RadiusRequestBuilder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public RadiusRequestBuilder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public RadiusRequest build() {
            return new RadiusRequest(radius, longitude, latitude);
        }
    }

}
