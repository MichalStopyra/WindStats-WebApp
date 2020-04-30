package com.windstatsapp.backend.repository;

import com.windstatsapp.backend.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepository extends JpaRepository <Spot, Long> {
    @Query("select s from Spot s " +
            "where lower(s.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(s.country) like lower(concat('%', :searchTerm, '%'))")
    List<Spot> search(@Param("searchTerm") String searchTerm);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Spot s SET s.avgWindSpeed =:windSpeed WHERE s.id =:spotID")
    void updateAvgWindSpot(@Param("windSpeed") Double windSpeed, @Param("spotID") Long spotID);

    @Query("select s from Spot s " +
            "where lower(s.name) like lower(:spotName)")
    Spot checkIfExists(@Param("spotName") String spotName);

    @Query("select s from Spot s " +
            "where lower(s.type) like lower(:chosenType) and (lower(s.name) like lower(concat('%', :searchTerm, '%')) " +
            " or lower(s.country) like lower(concat('%', :searchTerm, '%')) )")
    List<Spot> filterBySpotType(@Param("chosenType") String chosenType, @Param("searchTerm") String searchTerm);
}

