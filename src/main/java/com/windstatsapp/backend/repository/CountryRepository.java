package com.windstatsapp.backend.repository;

import com.windstatsapp.backend.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
