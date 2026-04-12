package com.uit.cinemaportalapi.repository;

import com.uit.cinemaportalapi.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}