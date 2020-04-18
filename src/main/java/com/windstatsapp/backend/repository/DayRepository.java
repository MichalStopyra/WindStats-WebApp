package com.windstatsapp.backend.repository;

import com.windstatsapp.backend.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
