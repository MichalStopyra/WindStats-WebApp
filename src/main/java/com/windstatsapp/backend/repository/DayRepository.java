package com.windstatsapp.backend.repository;

import com.windstatsapp.backend.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {
    @Query("select d from Day d " +
            "where lower(d.monthName) like lower(concat('%', :searchTerm, '%')) " )
    List<Day> search(@Param("searchTerm") String searchTerm);

   /* @Query("select round(avg(wind_speed)) from Day d " +
            "where lower(d.monthName) like lower(concat('%', :monthM, '%')) " )
    Double avgWindSpeed(@Param("monthM") String monthM);*/

   /* @Query("SELECT new com/windstatsapp/backend/weatherapi/tools/avgResults ( AVG(wind_speed) as avg_wind_speed FROM Day d WHERE lower(d.monthName) like lower(concat('%', :monthM, '%')) " )
    public avgResults avgWindSpeed(@Param("monthM") String monthM);*/
   @Query("SELECT AVG(d.windSpeed) FROM Day d WHERE lower(d.monthName) like lower(concat('%', :monthM, '%'))" )
    double avgWindSpeed(@Param("monthM") String monthM);

}
