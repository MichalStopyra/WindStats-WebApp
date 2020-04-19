package com.windstatsapp.backend.service;

import com.windstatsapp.backend.entity.Day;
import com.windstatsapp.backend.repository.DayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayService {
    private DayRepository dayRepository;

    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public List<Day> findAll() {
        return dayRepository.findAll();
    }


    public List<Day> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return dayRepository.findAll();
        } else  {
            return  dayRepository.search(filterText);
        }
    }

    public Double avgWindSpeed (String monthM) {
       if (monthM == null || monthM.isEmpty())
           return 0.0;
       else
           return dayRepository.avgWindSpeed(monthM);
    }
}
