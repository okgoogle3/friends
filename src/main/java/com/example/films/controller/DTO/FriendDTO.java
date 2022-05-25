package com.example.films.controller.DTO;

import com.example.films.model.MovieModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class FriendDTO {
    private String username;
    private Set<MovieDTO> movies;
}
