package com.example.tournetwork.repositories;

import com.example.tournetwork.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {
}
