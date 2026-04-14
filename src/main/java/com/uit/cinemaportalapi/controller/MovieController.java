package com.uit.cinemaportalapi.controller;


import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping
    ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(movieService.getMovies());
    }

    @GetMapping("/enable")
    ResponseEntity<List<Movie>> getMovieIsShowing (){
        return ResponseEntity.ok(movieService.getMovieIsShowing());
    }
    @GetMapping("/coming-soon")
    ResponseEntity<List<Movie>> findAllMovieComingSon(){
        return ResponseEntity.ok(movieService.findAllMovieComingSon());
    }

    @GetMapping("/detail/{id}")
    ResponseEntity<Movie> getMovieDetails(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(movieService.findMovieByID(id));
    }

    @PostMapping
    ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/{id}")
    ResponseEntity<Movie> updateMovie(@PathVariable(value = "id") Long id, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteMovie(@PathVariable(value = "id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Deleted movie with id: " + id);
    }

}
