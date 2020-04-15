package com.windstatsapp.backend.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Country extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<Spot> spotList = new LinkedList<>();

    public Country() {
    }

    public Country(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }
}