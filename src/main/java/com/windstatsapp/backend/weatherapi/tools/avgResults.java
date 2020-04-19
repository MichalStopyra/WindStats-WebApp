package com.windstatsapp.backend.weatherapi.tools;

public class avgResults {

    private final double avgWindSpeed;

    public avgResults(double avgWindSpeed){
        this.avgWindSpeed = avgWindSpeed;
    }

    public double getAvgWindSpeed() {
        return avgWindSpeed;
    }
}
