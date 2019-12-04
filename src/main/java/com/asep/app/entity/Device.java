package com.asep.app.entity;

import lombok.Data;

@Data
public class Device {

    private String deviceId;
    private String phoneType;

    public Device(String deviceId, String phoneType) {
        this.deviceId = deviceId;
        this.phoneType = phoneType;
    }

    public static class DeviceBuilder {
        private String deviceId;
        private String phoneType;

        public DeviceBuilder() {
        }

        public DeviceBuilder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public DeviceBuilder setPhoneType(String phoneType) {
            this.phoneType = phoneType;
            return this;
        }

        public Device build() {
            return new Device(deviceId, phoneType);
        }
    }
}
