package com.asep.app.service;

import com.asep.app.entity.Postcode;
import com.asep.app.entity.RadiusRequest;
import com.asep.app.entity.Property;
import com.asep.app.exceptions.PostcodeNotFoundException;

import java.util.List;

public interface PostcodeService {

    List<Postcode> addPostcode(List<Postcode> postcodes);

    List<Property> test(int amount);

    List<Property> getPropertiesWithinRadius(RadiusRequest request);

    //List<Postcode> getPostcodes();

    int getPostcodes();

    Postcode getPostcode(String postcode) throws PostcodeNotFoundException;
}
