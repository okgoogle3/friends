package com.example.films.controller;

import com.example.films.controller.DTO.FriendDTO;
import com.example.films.controller.DTO.MovieDTO;
import com.example.films.model.FriendModel;
import com.example.films.model.MovieModel;
import com.example.films.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        List<MovieModel> movies = friendService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieModel> getMovieById(@PathVariable long id) throws Exception{
        try{
            MovieModel movie = friendService.getMovieById(id);
            return ResponseEntity.ok(movie);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addMovie(@RequestBody MovieDTO movieDTO) {
        final String idIMDB = movieDTO.getIdIMDB();
        final String title = movieDTO.getTitle();
        try {
            final long id = friendService.addMovie(idIMDB, title);
            String location = String.format("/movies/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}