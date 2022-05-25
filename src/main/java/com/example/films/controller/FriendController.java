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

    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> getFriendRatingById(@PathVariable long id) throws Exception{
        try{
            return ResponseEntity.ok(friendService.getFriendRating(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Double>> getAllFriendsRating() throws Exception{
        try{
            return ResponseEntity.ok(friendService.getAllFriendsRating());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Long> addFriend(@RequestBody FriendDTO friendDTO) throws Exception {
        String username = friendDTO.getUsername();
        Set<MovieDTO> movies = friendDTO.getMovies();

        return ResponseEntity.ok(friendService.addFriend(username, movies));

    }
}