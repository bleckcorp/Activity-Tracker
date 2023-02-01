package com.laundry.washer.repository;

import com.laundry.washer.model.PostRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository interface that manages data and queries
@Repository
public interface PostRepository extends JpaRepository <PostRequest, Long>{
}
