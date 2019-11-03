package com.asep.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {

    private Long timestamp;
    private String latitude;
    private String longitude;

}
