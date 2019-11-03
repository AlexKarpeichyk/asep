package com.asep.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Device {


    private String deviceId;

    private String phoneNumber;

    private String phoneType;

    private String countryCode;


}
