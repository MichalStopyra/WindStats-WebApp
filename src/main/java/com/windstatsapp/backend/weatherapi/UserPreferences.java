package com.windstatsapp.backend.weatherapi;

public class UserPreferences {

    public static String monthChoice;

    public UserPreferences() {
        monthChoice = null;
    }

    public static String windChoice;
    public static String spotTypeChoice;

    public static int minWind;
    public static int maxWind;

    public static String country;

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        UserPreferences.country = country;
    }

    public static int getMinWind() {
        return minWind;
    }


    public static void setMinWind(int minWind) {
        UserPreferences.minWind = minWind;
    }

    public static int getMaxWind() {
        return maxWind;
    }

    public static void setMaxWind(int maxWind) {
        UserPreferences.maxWind = maxWind;
    }

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
