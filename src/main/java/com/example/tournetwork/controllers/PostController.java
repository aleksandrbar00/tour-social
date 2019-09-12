package com.example.tournetwork.controllers;

import com.example.tournetwork.model.Post;
import com.example.tournetwork.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {

    private PostRepo postRepo;

    @Autowired
    PostController(PostRepo postRepo){
        this.postRepo = postRepo;
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.GET)
    public String createPostPage(){
        return "create-post";
    }

    @RequestMapping(value = "/posts/list", method = RequestMethod.GET)
    public String listPosts(Model model){
        Iterable<Post> posts = this.postRepo.findAll();
        model.addAttribute("posts", posts);
        return "posts-list";
    }

}
