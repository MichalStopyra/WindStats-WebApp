package com.windstatsapp.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Spot extends AbstractEntity implements Cloneable {

    public enum Type {
        FlatWater, Chop, Waves, HugeWaves
    }

    @NotNull
    @NotEmpty
    private String name = "";

   /* @NotNull
    @NotEmpty
    private String countryName = "";*/

    @NotNull
    private int windPercentage;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Spot.Type type;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

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

    @Override
    public String toString() {
        return name;
    }
}