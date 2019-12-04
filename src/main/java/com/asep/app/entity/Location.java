package com.asep.app.entity;

import lombok.Data;

@Data
public class Location {

    private Long timestamp;
    private String latitude;
    private String longitude;

    public Location(Long timestamp, String latitude, String longitude) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static class LocationBuilder {

        private Long timestamp;
        private String latitude;
        private String longitude;

        public LocationBuilder() {
        }

        public LocationBuilder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public LocationBuilder setLatitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public LocationBuilder setLongitude(String longitude) {
            this.longitude = longitude;
            return this;
        }

        public Location build() {
            return new Location(timestamp, latitude, longitude);
        }
    }
}