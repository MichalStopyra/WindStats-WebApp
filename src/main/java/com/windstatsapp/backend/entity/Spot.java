package com.windstatsapp.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Spot extends AbstractEntity implements Cloneable {

    @NotNull
    public enum Type {
        FlatWater, Chop, Waves, HugeWaves
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
    private double avgGustsSpeed;

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

    //@NotNull
    //@NotEmpty
    //private String avgWindDirection;

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

    public double getAvgGustsSpeed() { return avgGustsSpeed; }

    public void setAvgGustsSpeed(double avgGustsSpeed) { this.avgGustsSpeed = avgGustsSpeed; }

    public List<Day> getDayList() {        return dayList; }

    public void setDayList(List<Day> dayList) { this.dayList = dayList; }

    @Override
    public String toString() {
        return name;
    }
}