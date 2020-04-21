package com.windstatsapp.backend.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Month extends AbstractEntity {

    @NotEmpty
    @NotNull
    private String monthName;

    @NotNull
    private double avgWindSpeed;

    @NotNull
    private int windPercentage;

    @ManyToOne
    @JoinColumn(name = "spot")
    private Spot spot;

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public double getWindSpeed() {
        return avgWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.avgWindSpeed = windSpeed;
    }

    public int getWindPercentage() {
        return windPercentage;
    }

    public void setWindPercentage(int windPercentage) {
        this.windPercentage = windPercentage;
    }
}
