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
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<List<FriendModel>> getAllFriends() {
        List<FriendModel> friends = friendService.getAllFriends();
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FriendModel> getFriendById(@PathVariable long id) throws Exception{
        try{
            FriendModel friend = friendService.getFriendById(id);
            return ResponseEntity.ok(friend);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    /*
    @GetMapping("/{id}/rating")
    public ResponseEntity<List<FriendModel>> getAllFriends() {
        List<FriendModel> friends = friendService.getAllFriends();
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<FriendModel>> getAllFriends() {
        List<FriendModel> friends = friendService.getAllFriends();
        return ResponseEntity.ok(friends);
    }
    */

    @PostMapping
    public ResponseEntity<Void> addFriend(@RequestBody FriendDTO friendDTO) {
        final String username = friendDTO.getUsername();
        final Set<MovieDTO> movies = friendDTO.getMovies();

        try {
            movies.forEach(movieDTO -> {
                final String idIMDB = movieDTO.getIdIMDB();
                final String title = movieDTO.getTitle();
                try {
                    friendService.addMovie(idIMDB, title);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        try {
            final long id = friendService.addFriend(username, movies);
            String location = String.format("/friends/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
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