package com.uit.cinemaportalapi.repository;

import com.uit.cinemaportalapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie , Long> {

    @Query(value = "SELECT * FROM MOVIE WHERE RELEASE_DATE >= :currentDate ORDER BY RELEASE_DATE ASC ", nativeQuery = true)
    List<Movie> findAllByReleaseDate(LocalDate currentDate);

    List<Movie> findAllByEnableIsTrueOrderByReleaseDate();





}