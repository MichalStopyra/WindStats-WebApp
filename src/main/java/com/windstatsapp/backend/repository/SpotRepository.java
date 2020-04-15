package com.windstatsapp.backend.repository;

import com.windstatsapp.backend.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepository extends JpaRepository <Spot, Long> {
    @Query("select s from Spot s " +
            "where lower(s.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(s.country) like lower(concat('%', :searchTerm, '%'))")
    List<Spot> search(@Param("searchTerm") String searchTerm);
}

