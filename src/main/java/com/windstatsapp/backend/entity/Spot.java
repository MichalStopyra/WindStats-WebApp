package com.windstatsapp.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Spot extends AbstractEntity implements Cloneable {

    @NotNull
    public enum Type {
        Flat_Water, Chop, Wave
    }

    @NotNull
    @NotEmpty
    private String name = "";


    @NotNull
    private int windPercentage;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Spot.Type type;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @NotNull
    private double latitude;

    @NotNull
    private double longtitude;

    @NotNull
    private double avgWindSpeed;

    @NotNull
    private double avgGustSpeed;

    @NotNull
    private int avgTemperature;

    @NotNull
    @NotEmpty
    private String imgPath = "";
    @NotNull
    @NotEmpty
    private String spotInfoTextPath = "";

    @NotNull
    @NotEmpty
    @Size(min = 0, max = 2147483)
    private String spotInfoText = "";

    @OneToMany(mappedBy = "spot", fetch = FetchType.EAGER)
    private List<Day> dayList = new LinkedList<>();

    @OneToMany(mappedBy = "spot", fetch = FetchType.EAGER)
    private List<Day> monthList = new LinkedList<>();

    public List<Day> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Day> monthList) {
        this.monthList = monthList;
    }


    /*public Spot(String name, String countryName, int windPercentage) {
        this.name = name;
        this.countryName = countryName;
        this.windPercentage = windPercentage;
    }*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getWindPercentage() {
        return windPercentage;
    }

    public void setWindPercentage(int windPercentage) {
        this.windPercentage = windPercentage;
    }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongtitude() { return longtitude; }

    public void setLongtitude(double longtitude) { this.longtitude = longtitude; }

    public double getAvgWindSpeed() { return avgWindSpeed; }

    public void setAvgWindSpeed(double avgWindSpeed) { this.avgWindSpeed = avgWindSpeed; }

    public double getAvgGustSpeed() { return avgGustSpeed; }

    public void setAvgGustSpeed(double avgGustSpeed) { this.avgGustSpeed = avgGustSpeed; }

    public List<Day> getDayList() {        return dayList; }

    public void setDayList(List<Day> dayList) { this.dayList = dayList; }

    public int getAvgTemperature() { return avgTemperature; }

    public void setAvgTemperature(int avgTemperature) { this.avgTemperature = avgTemperature; }

    public String getImgPath() { return imgPath; }

    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    public String getSpotInfoText() { return spotInfoText; }

    public String getSpotInfoTextPath() { return spotInfoTextPath; }

    public void setSpotInfoTextPath(String spotInfoTextPath) { this.spotInfoTextPath = spotInfoTextPath; }

    public void setSpotInfoText(String spotInfoText) { this.spotInfoText = spotInfoText; }

    @Override
    public String toString() {
        return name;
    }
}