package com.example.tournetwork.controllers;

import com.example.tournetwork.model.Post;
import com.example.tournetwork.model.User;
import com.example.tournetwork.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreatePostController {

    private PostRepo postRepo;

    @Autowired
    CreatePostController(PostRepo postRepo){
        this.postRepo = postRepo;
    }

    @PostMapping("api/posts/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post){

        this.postRepo.save(post);

        return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }

}
