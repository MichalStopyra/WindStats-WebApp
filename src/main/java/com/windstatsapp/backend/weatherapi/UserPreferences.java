package com.windstatsapp.backend.weatherapi;

public class UserPreferences {

    public static String monthChoice;

    public UserPreferences() {
    }

    public static String windChoice;
    public static String spotTypeChoice;


    public String getMonthChoice() {
        return monthChoice;
    }

    public void setMonthChoice(String monthChoice) {
        this.monthChoice = monthChoice;
    }

    public String getWindChoice() {
        return windChoice;
    }

    public void setWindChoice(String windChoice) {
        this.windChoice = windChoice;
    }

    public String getSpotTypeChoice() {
        return spotTypeChoice;
    }

    public void setSpotTypeChoice(String spotTypeChoice) {
        this.spotTypeChoice = spotTypeChoice;
    }
}
