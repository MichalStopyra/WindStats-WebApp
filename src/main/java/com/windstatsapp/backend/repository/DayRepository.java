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

   @Query("SELECT AVG(d.windSpeed) FROM Day d WHERE lower(d.monthName) like lower(concat('%', :monthM, '%')) AND d.spot.id =:spotID" )
    Double avgWindSpeed(@Param("monthM") String monthM, @Param("spotID") Long spotID);

    @Query("select d from Day d " +
            "where d.spot.id =:spotID AND d.dateMonth like lower (:date)")
    Day checkIfExists(@Param("spotID") Long spotID, @Param("date") String date);

    @Query ("SELECT COUNT(d.windSpeed) FROM Day d WHERE d.windSpeed <= :maxWind AND d.windSpeed >= :minWind AND d.spot.id = :spotID AND lower(d.monthName) like lower (:date)")
    int howManyDaysInWindRange(@Param("maxWind") double maxWind, @Param("minWind") double minWind, @Param("spotID") Long spotID, @Param("date") String date);

    @Query("SELECT AVG(d.gustSpeed) FROM Day d WHERE lower(d.monthName) like lower(concat('%', :monthM, '%')) AND d.spot.id =:spotID" )
    Double avgGustSpeed(@Param("monthM") String monthM, @Param("spotID") Long spotID);

    @Query("SELECT AVG(d.temperature) FROM Day d WHERE lower(d.monthName) like lower(concat('%', :monthM, '%')) AND d.spot.id =:spotID" )
    Double avgTemperature(@Param("monthM") String monthM, @Param("spotID") Long spotID);

}
