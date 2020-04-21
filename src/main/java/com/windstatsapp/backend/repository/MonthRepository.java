package com.windstatsapp.backend.repository;

import com.windstatsapp.backend.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthRepository extends JpaRepository<Day, Long> {

}
