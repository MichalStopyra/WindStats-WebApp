package com.windstatsapp.backend.entity;



import java.util.ArrayList;
import java.util.List;

//@Service
public class SpotManager {

    private List<Spot> spotList;


    public SpotManager() {
        this.spotList = new ArrayList<>();
        spotList.add(new Spot("Pozo", "Spain", 75 ) );
        spotList.add(new Spot("Tenerife", "Spain", 65 ) );
        spotList.add(new Spot("Karpathos", "Greece", 50 ) );
        spotList.add(new Spot("Prasonisi", "Greece", 40 ) );
        spotList.add(new Spot("Hel Peninsula", "Poland", 25 ) );
        spotList.add(new Spot("Zegrzynskie Lake", "Poland", -100 ) );
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public boolean addSpot(Spot spot) {
        return spotList.add(spot);
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }
}

