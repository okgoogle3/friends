package com.example.films.repo;

import com.example.films.model.FriendModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepo extends JpaRepository<FriendModel, Long> {

}
