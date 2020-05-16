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
import java.util.Date;
import java.util.List;
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

    public List<Spot> filterSpotByType (String filterText) {
        return spotRepository.filterBySpotType(UserPreferences.spotTypeChoice, filterText);
    }

    public List<Spot> filterSpotByCountry () {
        return spotRepository.filterBySpotCountry(UserPreferences.country);
    }

    @Transactional
    public void setSpotParameters (){
        List<Spot> spots = spotRepository.findAll();
        for (int i = 0; i < spots.size(); ++i ){
            spots.get(i).setAvgWindSpeed(Tools.round( dayRepository.avgWindSpeed(UserPreferences.monthChoice, spots.get(i).getId()),2) );
            spots.get(i).setWindPercentage(setWindPercentage(spots.get(i).getId()));
            spots.get(i).setAvgGustSpeed(Tools.round(dayRepository.avgGustSpeed(UserPreferences.monthChoice, spots.get(i).getId()),2) );
            spots.get(i).setAvgTemperature((int) Math.round(dayRepository.avgTemperature( UserPreferences.monthChoice, spots.get(i).getId())) );
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

    public static double parameterFromJson(String parameter, String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("currently").getLong(parameter);/*("windSpeed");*/
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

    public double setAvgWindSpeedForHelper(String month, Spot spot) {
        return Tools.round( dayRepository.avgWindSpeed(month, spot.getId()),2) ; }
    public double setAvgGustSpeedForHelper(String month, Spot spot) {
        return Tools.round(dayRepository.avgGustSpeed(month, spot.getId()),2); }
    public int setAvgTempForHelper(String month, Spot spot) {
       return (int) Math.round(dayRepository.avgTemperature( month, spot.getId())); }



    @PostConstruct
    public void populateData() {

       //wc if (countryRepository.count() == 0) {

            countryRepository.saveAll(
                    Stream.of("Spain", "Greece", "Poland")
                            .filter(str -> countryRepository.checkIfExists(str) == null)
                            .map(Country::new)
                            .collect(Collectors.toList()));


            //  if (spotRepository.count() < 2 ) {
            List<Country> countries = countryRepository.findAll();
            spotRepository.saveAll( /*Name country_nr type_nr latitude longtitude*/
                    Stream.of("Pozo_Izquierdo 0 2 27.8333 -15.4667", "Hel_Penninsula 2 1 54.6957333 18.6788396 ",
                            "Zegrzynskie_Lake 2 1 52.229676 21.012229", "Karpathos 1 0 35.583331 27.1333328",
                            "Prasonisi 1 0 35.876163162 27.75666364", "El_Medano 0 2 28.03833318 -16.5333312")
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
                                spot.setAvgGustSpeed(0.0);
                                spot.setAvgTemperature(0);

                                spot.setImgPath("./images/".concat( spot.getName().concat(".png")));

                                spot.setSpotInfoTextPath("./SpotInfoText/".concat( spot.getName().concat(".txt")));
                                spot.setSpotInfoText(Tools.readStringFromFile(spot.getSpotInfoTextPath()));

                                return spot;
                            }).collect(Collectors.toList()));
            //   }

        Date today = new Date();
            List<Spot> spots = spotRepository.findAll();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June",
                                            "July", "August", "September", "October", "November", "December");
       // for (int tempYear = 2019; tempYear <= 2020; ++tempYear)
        for(int j = 0; j < months.size(); ++j) {
            String tempMonth = months.get(j);
        //String tempMonth = "January";
            //DateTime temp = new DateTime(tempYear, tempMonth, 1, 12, 0);
            //if(temp.toDate().after(today))
            //  return;

            for (int i = 0; i < spots.size(); ++i) {
                int fI = i;
                dayRepository.saveAll(
                        Tools.convertListToStream(Tools.DateArr(tempMonth))
                                .filter(str -> dayRepository.checkIfExists(spots.get(fI).getId(), str) == null)
                                .map(nameDay -> {
                                    Day day = new Day();
                                    nameDay = nameDay.concat("T12:00:00.000-0000");

                                    day.setMonthName(tempMonth);//---------------do zmainy
                                    day.setDateMonth(nameDay.substring(0, 10));
                                    day.setDateDay(Tools.tsToSec8601(nameDay));
                                    day.setSpot(spots.get(fI));
                                    Weather.setCoordinates(day.getSpot().getLatitude(), day.getSpot().getLongtitude());

                                    String jsonString = Weather.doHttpGet(day.getDateDay());

                                    day.setWindSpeed(Tools.mph_to_knots(parameterFromJson( "windSpeed", jsonString)));
                                    day.setGustSpeed(Tools.mph_to_knots(parameterFromJson( "windGust",jsonString)));
                                    day.setTemperature(Tools.fahrenheit_to_celsius(parameterFromJson("temperature",jsonString)));
                                    return day;
                                }).collect(Collectors.toList()));

                //month
            }
        }


    }
}
