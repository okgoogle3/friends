package com.example.films.service;

import com.example.films.controller.DTO.MovieDTO;
import com.example.films.model.FriendModel;
import com.example.films.model.MovieModel;
import com.example.films.repo.FriendRepo;
import com.example.films.repo.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendService {
    @Autowired
    public final FriendRepo friendRepo;

    @Autowired
    public final MovieRepo movieRepo;

    public List<FriendModel> getAllFriends(){
        return friendRepo.findAll();
    }

    public List<MovieModel> getAllMovies(){
        return movieRepo.findAll();
    }

    public long addFriend(String username, Set<MovieDTO> movies) throws Exception {
        if (username!=null) {
            FriendModel friend = new FriendModel(username, movies
                    .stream()
                    .map(movieDTO -> new MovieModel(movieDTO.getIdIMDB(), movieDTO.getTitle()))
                    .collect(Collectors.toSet()));
            FriendModel savedFriend = friendRepo.save(friend);
            return savedFriend.getId();
        }
        else throw new Exception();
    }

    public long addMovie(String idIMDB, String title) throws Exception {
        if (idIMDB!=null && title!=null) {
            MovieModel movie = new MovieModel(idIMDB, title);
            MovieModel savedMovie = movieRepo.save(movie);
            return savedMovie.getId();
        }
        else throw new Exception();
    }

    public FriendModel getFriendById(long id) throws Exception {
        final Optional<FriendModel> maybeFriend = friendRepo.findById(id);
        if (maybeFriend.isPresent()) return maybeFriend.get();
        else throw new Exception();
    }
}
