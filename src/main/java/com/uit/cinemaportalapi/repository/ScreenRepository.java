package com.uit.cinemaportalapi.repository;

import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
	List<Screen> findAllByCinema(Cinema cinema);
	boolean existsByCinemaAndScreenCodeIgnoreCase(Cinema cinema, String screenCode);
}
