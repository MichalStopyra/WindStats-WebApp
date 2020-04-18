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
        //http://dataservice.accuweather.com/forecasts/v1/daily/1day/348735?apikey=<ApiKey>
        //http://dataservice.accuweather.com/forecasts/v1/daily/1day/<CITYID>?apikey=<ApiKey>

        //https://api.darksky.net/forecast/4b90d7a5baccffb6e73c7a60c1ebe503/37.8267,-122.4233

        //String url = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/348735?apikey=" + ApiKey.getApiKey();

        String url = "https://api.darksky.net/forecast/" + ApiKey.getApiKey() + getCoordinates() + "," + Integer.toString(unixTime);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = null;
        try {
            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            //System.out.println("Json response");
            //System.out.println(EntityUtils.toString(entity));
            return EntityUtils.toString(entity);
        }
        catch (IOException ioe) { System.err.println("Something went wrong getting the weather: ");  ioe.printStackTrace(); }
        catch (Exception e ){ System.err.println("Unknown error: "); e.printStackTrace(); }
        return "Error loading http";
    }
}