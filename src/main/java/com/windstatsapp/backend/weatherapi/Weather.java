package com.windstatsapp.backend.weatherapi;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Weather {

    public static String coordinates;

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public static void setCoordinates(double latitude, double longtitude) {
        coordinates = "/" + latitude + "," + longtitude;
    }


    public static String getCoordinates() {
        return coordinates;
    }

    public static String doHttpGet(int unixTime){

        String url = "https://api.darksky.net/forecast/" + ApiKey.getApiKey() + getCoordinates() + "," + Integer.toString(unixTime);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = null;
        try {
            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            return EntityUtils.toString(entity);
        }
        catch (IOException ioe) { System.err.println("Something went wrong getting the weather: ");  ioe.printStackTrace(); }
        catch (Exception e ){ System.err.println("Unknown error: "); e.printStackTrace(); }
        return "Error loading http";
    }
}