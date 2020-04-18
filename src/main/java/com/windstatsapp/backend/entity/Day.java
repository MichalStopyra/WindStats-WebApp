package com.windstatsapp.backend.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Day extends AbstractEntity {

    @NotNull
    private int dateDay;

    @NotEmpty
    @NotNull
    private String dateMonth;

    @NotNull
    private double windSpeed;

    //@NotNull
    //private double gustSpeed;

    //@NotNull
    //private double temperature;

    @ManyToOne
    @JoinColumn(name = "spot")
    private Spot spot;



    public int getDateDay() { return dateDay; }

    public void setDateDay(int dateDay) { this.dateDay = dateDay;    }

    public String getDateMonth() {        return dateMonth; }

    public void setDateMonth(String dateMonth) {        this.dateMonth = dateMonth; }

    public double getWindSpeed() { return windSpeed; }

    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed;    }

    //public double getGustSpeed() {        return gustSpeed; }

    //public void setGustSpeed(double gustSpeed) {        this.gustSpeed = gustSpeed; }

    public Spot getSpot() { return spot; }

    public void setSpot(Spot spot) { this.spot = spot; }

}
