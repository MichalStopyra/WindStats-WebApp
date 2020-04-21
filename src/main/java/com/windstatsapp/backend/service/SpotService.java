package com.windstatsapp.backend.service;

import com.windstatsapp.backend.entity.Country;
import com.windstatsapp.backend.entity.Day;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.repository.CountryRepository;
import com.windstatsapp.backend.repository.DayRepository;
import com.windstatsapp.backend.repository.SpotRepository;
import com.windstatsapp.backend.weatherapi.UserPreferences;
import com.windstatsapp.backend.weatherapi.Weather;
import com.windstatsapp.backend.weatherapi.tools.Tools;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
public class SpotService {
    private com.windstatsapp.backend.repository.SpotRepository spotRepository;
    private CountryRepository countryRepository;
    private DayRepository dayRepository;

    public SpotService(SpotRepository spotRepository,
                       CountryRepository countryRepository,
                       DayRepository dayRepository) {
        this.spotRepository = spotRepository;
        this.countryRepository = countryRepository;
        this.dayRepository = dayRepository;
    }

    public List<Spot> findAll() {
        return spotRepository.findAll();
    }

    public List<Spot> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return spotRepository.findAll();
        } else {
            return spotRepository.search(filterText);
        }
    }


    @Transactional
    public void setWindPercentage_And_AvgWindAllSpots (){
        List<Spot> spots = spotRepository.findAll();
        for (int i = 0; i < spots.size(); ++i ){
            spots.get(i).setAvgWindSpeed(dayRepository.avgWindSpeed(UserPreferences.monthChoice, spots.get(i).getId()));
            spots.get(i).setWindPercentage(setWindPercentage(spots.get(i).getId()));
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

    public static double windFromJson(int unixTime) {
        String jsonString = Weather.doHttpGet(unixTime);//assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("currently").getLong("windSpeed");


        //String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        //JSONArray arr = obj.getJSONArray("posts");
        //for (int i = 0; i < arr.length(); i++)
        //{
        //   String post_id = arr.getJSONObject(i).getString("post_id");
        //}
    }

    public int setWindPercentage(Long spotID){
        UserPreferences.setMinWind(Integer.parseInt(UserPreferences.windChoice.substring(0,2)));
        UserPreferences.setMaxWind(Integer.parseInt(UserPreferences.windChoice.substring(3)));
        int dayCounter = dayRepository.howManyDaysInWindRange(UserPreferences.getMaxWind(),UserPreferences.getMinWind(),
                                                                spotID, UserPreferences.monthChoice);
        LocalDate date = LocalDate.of(2019, Tools.monthStringToInt(UserPreferences.monthChoice), 1);
        int monthLength = date.lengthOfMonth();
        double result = (dayCounter*100.0/monthLength);
        return (int) result;
    }


    @PostConstruct
    public void populateData() {

       //wc if (countryRepository.count() == 0) {

            countryRepository.saveAll(
                    Stream.of("Spain", "Greece", "Poland")
                            .filter(str -> countryRepository.checkIfExists(str) == null)
                            .map(Country::new)
                            .collect(Collectors.toList()));


            Random r = new Random(0);

            //  if (spotRepository.count() < 2 ) {
            List<Country> countries = countryRepository.findAll();
            spotRepository.saveAll( /*Name country_nr type_nr latitude longtitude*/
                    Stream.of("Pozo_Izquierdo 0 2 27.8333 -15.4667", "Hel_Penninsula 2 1 18.6788396 54.6957333"/*,
                            "Zegrzynskie_Lake 2 1 21.012229 52.229676"/*Warsaw*, "Karpathos 1 0 35.583331 27.1333328",
                            "Prasonisi 1 0 35.876163162 27.75666364", "El_Medano 0 2 28.03833318 -16.5333312"*/)
                            //.map(str-> { String[] s = str.split(" ");
                            //return s[0];}).filter(str-> spotRepository.checkIfExists() == null)
                            .filter(str-> spotRepository.checkIfExists(str.split(" ")[0]) == null )
                            .map(name -> {
                                String[] split = name.split(" ");
                               // if( spotRepository.checkIfExists(split[0])!= null )
                                 //   return null;

                                Spot spot = new Spot();
                                spot.setName(split[0]);
                                spot.setLatitude(Double.valueOf(split[3]));
                                spot.setLongtitude(Double.valueOf(split[4]));
                                //spot.setCountry(split[1]);
                                spot.setCountry(countries.get(Integer.parseInt(split[1])));
                                spot.setType(Spot.Type.values()[Integer.parseInt(split[2])]);
                                //spot.setWindPercentage(r.nextInt()%100);

                                //?????????????? Weather.setCoordinates(spot.getLatitude(), spot.getLatitude());

                                //spot.setWindPercentage((int)windFromJson());
                                spot.setWindPercentage(0);
                                spot.setAvgWindSpeed(0.0);

                                return spot;
                            }).collect(Collectors.toList()));
            //   }

            // UserPreferences up = new UserPreferences();
            List<Spot> spots = spotRepository.findAll();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June",
                                            "July", "August", "September", "October", "November", "December");
        String tempMonth = "February";
        //for(int j = 0; i < months.size(); ++j)
            for (int i = 0; i < spots.size(); ++i) {
                int fI = i;
                dayRepository.saveAll(
                        Tools.convertListToStream(Tools.DateArr(tempMonth))
                                .filter(str-> dayRepository.checkIfExists(spots.get(fI).getId(), str) == null)
                                .map(nameDay -> {
                                    Day day = new Day();
                                    nameDay = nameDay.concat("T12:00:00.000-0000");

                                    day.setMonthName(tempMonth);//---------------do zmainy
                                    day.setDateMonth(nameDay.substring(0, 10));
                                    day.setDateDay(Tools.tsToSec8601(nameDay));
                                    day.setSpot(spots.get(fI));
                                    Weather.setCoordinates(day.getSpot().getLatitude(), day.getSpot().getLongtitude());
                                    day.setWindSpeed(Tools.mph_to_knots(windFromJson(day.getDateDay())));
                                    return day;
                                }).collect(Collectors.toList()));
           // }
            //month




        }


    }
}
