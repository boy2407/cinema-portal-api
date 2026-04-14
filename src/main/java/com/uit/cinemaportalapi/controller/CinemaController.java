package com.uit.cinemaportalapi.controller;

import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.entity.Screen;
import com.uit.cinemaportalapi.service.CinemaService;
import com.uit.cinemaportalapi.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cinemas")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private ScreenService screenService;

    @GetMapping
    ResponseEntity<List<Cinema>> getCinemas() {
        return ResponseEntity.ok(cinemaService.getCinemas());
    }

    @GetMapping("/{id}")
    ResponseEntity<Cinema> getCinemaByID(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(cinemaService.getCinemaByID(id));
    }

    @GetMapping("/{id}/screens")
    ResponseEntity<List<Screen>> getScreensByCinema(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(screenService.getScreensByCinema(id));
    }

    @PostMapping("/{id}/screens")
    ResponseEntity<Screen> createScreenByCinema(@PathVariable(value = "id") Long id, @RequestBody Screen screen) {
        Cinema cinema = new Cinema();
        cinema.setId(id);
        screen.setCinema(cinema);
        return ResponseEntity.ok(screenService.createScreen(screen));
    }

    @PostMapping
    ResponseEntity<Cinema> createCinema(@RequestBody Cinema cinema) {
        return ResponseEntity.ok(cinemaService.createCinema(cinema));
    }

    @PutMapping("/{id}")
    ResponseEntity<Cinema> updateCinema(@PathVariable(value = "id") Long id, @RequestBody Cinema cinema) {
        return ResponseEntity.ok(cinemaService.updateCinema(id, cinema));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCinema(@PathVariable(value = "id") Long id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.ok("Deleted cinema with id: " + id);
    }
}

