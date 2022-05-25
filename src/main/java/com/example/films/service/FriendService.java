package com.example.films.service;

import com.example.films.controller.DTO.MovieDTO;
import com.example.films.model.FriendModel;
import com.example.films.model.MovieModel;
import com.example.films.repo.FriendRepo;
import com.example.films.repo.MovieRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
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

    /*
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
*/
    public long addFriend(String username, Set<MovieDTO> movies) throws Exception {
        if (username != null) {
            Set<MovieModel> movieModels = new HashSet<>();
            for (MovieDTO movieDto : movies) {
                movieModels.add(movieRepo.save(MovieModel.builder()
                        .title(movieDto.getTitle())
                        .idIMDB(movieDto.getIdIMDB())
                        .build()));
            }

            FriendModel friend = new FriendModel(username, movieModels);
            FriendModel savedFriend = friendRepo.save(friend);
            return savedFriend.getId();
        } else throw new Exception();
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

    public MovieModel getMovieById(long id) throws Exception {
        final Optional<MovieModel> maybeMovie = movieRepo.findById(id);
        if (maybeMovie.isPresent()) return maybeMovie.get();
        else throw new Exception();
    }

    public double getFriendRating(long id) throws Exception {
        final Optional<FriendModel> maybeFriend = friendRepo.findById(id);
        if (maybeFriend.isPresent()) {
            Set<MovieModel> movies = maybeFriend.get().getMovies();
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            double sum=0;
            for (MovieModel movie : movies) {
                String uri = "http://www.omdbapi.com/?apikey=53fe6e18&i=" + movie.getIdIMDB();
                String result = restTemplate.getForObject(uri, String.class);
                JsonNode jsonNode = objectMapper.readTree(result);
                sum += jsonNode.get("imdbRating").asDouble();
            }
            return sum/movies.size();
        }
        else throw new Exception();
    }

    public List<Double> getAllFriendsRating() throws Exception {
        final List<FriendModel> friends = friendRepo.findAll();
        List<Double> ratings = new ArrayList<>();
        for (FriendModel friend : friends) {
            Set<MovieModel> movies = friend.getMovies();
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            double sum=0;
            for (MovieModel movie : movies) {
                String uri = "http://www.omdbapi.com/?apikey=53fe6e18&i=" + movie.getIdIMDB();
                String result = restTemplate.getForObject(uri, String.class);
                JsonNode jsonNode = objectMapper.readTree(result);
                sum += jsonNode.get("imdbRating").asDouble();
            }
            ratings.add(sum/movies.size());
        }
        return ratings;
    }
}
