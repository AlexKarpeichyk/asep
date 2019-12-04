package com.asep.app.helper;

public class DistanceCalculator {
    public double getDistance(double[] latlong1, double[] latlong2) {
        if(latlong1 == latlong2) {
            return 0;
        }
        else {
            double theta = latlong1[1] - latlong2[1];
            double dist = Math.sin(Math.toRadians(latlong1[0]))
                        * Math.sin(Math.toRadians(latlong2[0]))
                        + Math.cos(Math.toRadians(latlong1[0]))
                        * Math.cos(Math.toRadians(latlong2[0]))
                        * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return dist;
        }
    }
}
