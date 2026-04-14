package com.uit.cinemaportalapi.controller;

import com.uit.cinemaportalapi.entity.Screen;
import com.uit.cinemaportalapi.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/screens")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @GetMapping
    ResponseEntity<List<Screen>> getScreens() {
        return ResponseEntity.ok(screenService.getScreens());
    }

    @GetMapping("/cinema/{cinemaID}")
    ResponseEntity<List<Screen>> getScreensByCinema(@PathVariable(value = "cinemaID") Long cinemaID) {
        return ResponseEntity.ok(screenService.getScreensByCinema(cinemaID));
    }

    @GetMapping("/{id}")
    ResponseEntity<Screen> getScreenByID(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(screenService.findScreenByID(id));
    }

    @PostMapping
    ResponseEntity<Screen> createScreen(@RequestBody Screen screen) {
        return ResponseEntity.ok(screenService.createScreen(screen));
    }

    @PutMapping("/{id}")
    ResponseEntity<Screen> updateScreen(@PathVariable(value = "id") Long id, @RequestBody Screen screen) {
        return ResponseEntity.ok(screenService.updateScreen(id, screen));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteScreen(@PathVariable(value = "id") Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.ok("Deleted screen with id: " + id);
    }
}

