package com.example.films.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "friends")
public class FriendModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "LikedMoviesByFriends",
            joinColumns = { @JoinColumn(name = "friend_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    Set<MovieModel> movies = new HashSet<>();

    public FriendModel(){

    }

    public FriendModel(String username, Set<MovieModel> movies){
        this.username = username;
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieModel> movies) {
        this.movies = movies;
    }
    public void addMovies(Set<MovieModel> movies) {
        this.movies.addAll(movies);
    }
}
