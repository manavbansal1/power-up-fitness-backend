package com.project.power_up_fitness_backend.Repos;
import com.project.power_up_fitness_backend.Modals.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String>{

    @Query("{'name':?0}")
    Optional<User> findByUserName(String name);
    
}
