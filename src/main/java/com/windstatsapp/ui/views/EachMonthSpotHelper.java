package com.windstatsapp.ui.views;

import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.service.SpotService;

public class EachMonthSpotHelper  {

    private Spot spot;
    private String month;
    private double avgWindSpeed;
    private double avgGustSpeed;
    private int avgTemperature;
    private SpotService spotService;

    public EachMonthSpotHelper(SpotService spotService) {
        this.spotService = spotService;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAvgWindSpeed() {
        return avgWindSpeed;
    }

    public void setAvgWindSpeed(double avgWindSpeed) {
        this.avgWindSpeed = avgWindSpeed;
    }

    public double getAvgGustSpeed() {
        return avgGustSpeed;
    }

    public void setAvgGustSpeed(double avgGustSpeed) {
        this.avgGustSpeed = avgGustSpeed;
    }

    public int getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(int avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public void setParameters() {
        this.setAvgWindSpeed(spotService.setAvgWindSpeedForHelper(month, this.spot));
        this.setAvgGustSpeed(spotService.setAvgGustSpeedForHelper(month, this.spot));
        this.setAvgTemperature(spotService.setAvgTempForHelper(month, this.spot));
    }
}
