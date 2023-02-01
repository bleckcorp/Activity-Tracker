package com.bctech.activitytracker.repository;

import com.bctech.activitytracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Method used in the service class for user validation
    Optional<User> findUserByEmail(String name);
    Optional<User> findUserByEmailAndPassword(String email,String id);
}
