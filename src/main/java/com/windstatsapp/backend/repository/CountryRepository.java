package com.windstatsapp.backend.repository;

import com.windstatsapp.backend.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("select c from Country c " +
            "where lower(c.name) like lower(:countryName)")
    Country checkIfExists(@Param("countryName") String countryName);
}
