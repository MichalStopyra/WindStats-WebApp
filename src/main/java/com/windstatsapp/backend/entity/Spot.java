package com.windstatsapp.backend.entity;


//@Entity
public class Spot {

  //  @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String countryName;
    private int windPercentage;


    public Spot() {
    }

    public Spot(String name, String countryName, int windPercentage){
        this.name = name;
        this.countryName = countryName;
        this.windPercentage = windPercentage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getWindPercentage() {
        return windPercentage;
    }

    public void setWindPercentage(int windPercentage) {
        this.windPercentage = windPercentage;
    }
}
