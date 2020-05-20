package com.windstatsapp.backend.weatherapi;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WeatherTest {

    @Test
    public void testGetCoordinates() throws Throwable {
        Weather underTest = new Weather();
        underTest.setCoordinates(37.8267, -122.4233);
        assertEquals(" ", "/37.8267,-122.4233", Weather.getCoordinates());
    }
}
