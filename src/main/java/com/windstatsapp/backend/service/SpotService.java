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


    public static double parameterFromJson(String parameter, String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("currently").getLong(parameter);
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

        checkExistingCountriesAndAddNewToDB();

        List<Country> countries = countryRepository.findAll();
        checkExistingSpotsAndAddNewToDB(countries);

        Date today = new Date();
            List<Spot> spots = spotRepository.findAll();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June",
                                            "July", "August", "September", "October", "November", "December");

        for(int j = 0; j < months.size(); ++j) {
            String tempMonth = months.get(j);

            for (int i = 0; i < spots.size(); ++i) {
                int fI = i;
                CheckExistingStatsAndAddNewToDB(spots, tempMonth, fI);
            }
        }


    }

    private void CheckExistingStatsAndAddNewToDB(List<Spot> spots, String tempMonth, int fI) {
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
    }

    private void checkExistingCountriesAndAddNewToDB() {
        countryRepository.saveAll(
                Stream.of("Spain", "Greece", "Poland", "Austria", "Belgium", "Germany", "Italy", "Denmark", "Montenegro", "Netherlands",
                        "Portugal", "Switzerland", "United_Kingdom")
                        .filter(str -> countryRepository.checkIfExists(str) == null)
                        .map(Country::new)
                        .collect(Collectors.toList()));
    }

    private void checkExistingSpotsAndAddNewToDB(List<Country> countries) {
        spotRepository.saveAll( /*Name country_nr type_nr latitude longtitude*/
                Stream.of("Pozo_Izquierdo 0 2 27.8333 -15.4667", "Hel_Penninsula 2 1 54.6957333 18.6788396 ",
                        "Zegrzynskie_Lake 2 1 52.229676 21.012229", "Karpathos 1 0 35.583331 27.1333328",
                        "Prasonisi 1 0 35.876163162 27.75666364", "El_Medano 0 2 28.03833318 -16.5333312",
                        "Majanicho 0 2 28.7393 -13.9396", "Risco_Del_Paso 0 0 28.1107 -14.2642",
                        "Neuseidl_Lake 3 1 47.8650 16.7776", "Knokke 4 2 51.3464 3.2859",
                        "Loissin 5 1 54.1124 13.5281", "Lefkada 1 0 38.7066 20.6407",
                        "Limnos 1 0 39.9198 25.1415", "Naxos 1 0 37.1036 25.3777",
                        "Porto_Botte 6 0 39.0530 8.5644", "Porto_Pollo 6 1 41.1851 9.3250",
                        "Garda_Lake 6 1 45.8778 10.8909", "Ulcinj 8 1 41.9311 19.2148",
                        "Zeeland 9 2 51.4940 3.8497", "Texel 9 2 53.0548 4.7977",
                        "Burgau 10 2 37.0744 -8.7750", "Tarifa 0 2 36.0143 -5.6044",
                        "Silvaplana_Lake 11 1 46.4504 9.7933", "Camber_Sands 12 2 50.9327 0.7960")
                        .filter(str-> spotRepository.checkIfExists(str.split(" ")[0]) == null )
                        .map(name -> {
                            String[] split = name.split(" ");

                            Spot spot = new Spot();
                            spot.setName(split[0]);
                            spot.setLatitude(Double.valueOf(split[3]));
                            spot.setLongtitude(Double.valueOf(split[4]));
                            spot.setCountry(countries.get(Integer.parseInt(split[1])));
                            spot.setType(Spot.Type.values()[Integer.parseInt(split[2])]);

                            spot.setWindPercentage(0);
                            spot.setAvgWindSpeed(0.0);
                            spot.setAvgGustSpeed(0.0);
                            spot.setAvgTemperature(0);

                            spot.setImgPath("./images/".concat( spot.getName().concat(".png")));

                            spot.setSpotInfoTextPath("./SpotInfoText/".concat( spot.getName().concat(".txt")));
                            spot.setSpotInfoText(Tools.readStringFromFile(spot.getSpotInfoTextPath()));

                            return spot;
                        }).collect(Collectors.toList()));
    }
}
