package com.windstatsapp.backend.service;

import com.windstatsapp.backend.entity.Country;
import com.windstatsapp.backend.entity.Spot;
import com.windstatsapp.backend.repository.CountryRepository;
import com.windstatsapp.backend.repository.SpotRepository;
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
                    Stream.of("Pozo_Izquierdo Spain", "Hel_Penninsula Poland", "Zegrzynskie_Lake Poland",
                            "Karpathos Greece", "Prasonisi Greece")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Spot spot = new Spot();
                                spot.setName(split[0]);
                                //spot.setCountry(split[1]);
                                spot.setCountry(countries.get(r.nextInt(countries.size())));
                                spot.setType(Spot.Type.values()[r.nextInt(Spot.Type.values().length)]);
                                spot.setWindPercentage(r.nextInt()%100);
                                return spot;
                            }).collect(Collectors.toList()));
        }
    }



}
