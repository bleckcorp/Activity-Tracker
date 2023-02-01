package com.laundry.washer.repository;

import com.laundry.washer.model.Userbio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Repository interface that manages data and queries
@Repository
public interface UserRepository extends JpaRepository<Userbio, Long> {

//    Method used in the service class for user validation
    Optional<Userbio> findUserbioByPreferredNameAndPassword(String name, String password);
}
