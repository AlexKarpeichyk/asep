package com.asep.app.helper;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;

public class JavaGeocode {

    public double[] addressConversion(String address) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(Constant.API_KEY);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng latlong = response.getFirstPosition();

        return new double[]{latlong.getLat(), latlong.getLng()};

    }
}
