package com.windstatsapp.backend.service;

import com.windstatsapp.backend.entity.Country;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.repository.CountryRepository;
import com.windstatsapp.backend.repository.SpotRepository;
import com.windstatsapp.backend.weatherapi.Weather;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SpotService {
    private static final Logger LOGGER = Logger.getLogger(SpotService.class.getName());
    private com.windstatsapp.backend.repository.SpotRepository spotRepository;
    private CountryRepository countryRepository;

    public SpotService(SpotRepository spotRepository,
                       CountryRepository countryRepository) {
        this.spotRepository = spotRepository;
        this.countryRepository = countryRepository;
    }

    public List<Spot> findAll() {
        return spotRepository.findAll();
    }

    public List<Spot> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return spotRepository.findAll();
        } else  {
            return  spotRepository.search(filterText);
        }
    }


    /*public void save(Spot spot) {
        if (spot == null) {
            LOGGER.log(Level.SEVERE,
                    "Spot is null. Are you sure you have connected your form to the application?");
            return;
        }
        spotRepository.save(spot);
    }*/

    public static double windFromJson (){
        String jsonString = Weather.doHttpGet();//assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("currently").getLong("windSpeed");


        //String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        //JSONArray arr = obj.getJSONArray("posts");
        //for (int i = 0; i < arr.length(); i++)
        //{
        //   String post_id = arr.getJSONObject(i).getString("post_id");
        //}
    }




    @PostConstruct
    public void populateTestData() {
        if (countryRepository.count() == 0) {
            countryRepository.saveAll(
                    Stream.of("Spain", "Greece", "Poland")
                            .map(Country::new)
                            .collect(Collectors.toList()));
        }

        if (spotRepository.count() == 0) {
            Random r = new Random(0);
            List<Country> countries = countryRepository.findAll();
            spotRepository.saveAll(
                    Stream.of("Pozo_Izquierdo Spain 27.8333 -15.4667", "Hel_Penninsula Poland 18.6788396 54.6957333",
                            "Zegrzynskie_Lake Poland 21.012229 52.229676"/*Warsaw*/, "Karpathos Greece 35.583331 27.1333328",
                            "Prasonisi Greece 35.876163162 27.75666364")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Spot spot = new Spot();
                                spot.setName(split[0]);
                                spot.setLatitude(Double.valueOf (split[2]) );
                                spot.setLongtitude(Double.valueOf (split[3]) );
                                //spot.setCountry(split[1]);
                                spot.setCountry(countries.get(r.nextInt(countries.size())));
                                spot.setType(Spot.Type.values()[r.nextInt(Spot.Type.values().length)]);
                                //spot.setWindPercentage(r.nextInt()%100);
                                Weather.setCoordinates(spot.getLatitude(), spot.getLatitude());
                                spot.setWindPercentage((int)windFromJson());
                                return spot;
                            }).collect(Collectors.toList()));
        }
    }



}
