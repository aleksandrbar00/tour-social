package com.example.tournetwork.controllers;

import com.example.tournetwork.model.TourClub;
import com.example.tournetwork.model.User;
import com.example.tournetwork.repositories.TourClubsRepo;
import com.example.tournetwork.repositories.UserRepo;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;


@Controller
public class TourClubController {

    private TourClubsRepo tourClubsRepo;
    private UserRepo userRepo;

    @Autowired
    TourClubController(TourClubsRepo tourClubsRepo, UserRepo userRepo){
        this.tourClubsRepo = tourClubsRepo;
        this.userRepo = userRepo;
    }


    @RequestMapping(value = "/clubs/add", method = RequestMethod.GET)
    public String addNewClubPage(){
        return "add-new-club";
    }

    @RequestMapping(value = "/clubs/add", method = RequestMethod.POST)
    public String addNewClub(@RequestParam MultiValueMap body){
        TourClub tourClub = new TourClub();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userRepo.findByUsername(auth.getName());
        tourClub.setTitle(body.getFirst("title").toString());
        tourClub.setAbout(body.getFirst("about").toString());
        tourClub.getFollowers().add(user);
        tourClub.setOwner(user);
        this.tourClubsRepo.save(tourClub);
        return "redirect:/";
    }

    @RequestMapping(value = "/clubs/list", method = RequestMethod.GET)
    public String tourClubsList(Model model){
        Iterable<TourClub> tourClubs = this.tourClubsRepo.findAll();
        model.addAttribute("tourClubs", tourClubs);
        return "tour-clubs-list";
    }

    @RequestMapping(value = "/clubs/{id}", method = RequestMethod.GET)
    public String tourClubPage(@PathVariable(name = "id") Long id, Model model){
        Optional<TourClub> tourClub = this.tourClubsRepo.findById(id);
        model.addAttribute("tourClub", tourClub.get());
        return "club-page";
    }

    @RequestMapping(value = "/clubs/{id}/follow", method = RequestMethod.GET)
    public String followClub(HttpServletRequest httpServletRequest, @PathVariable(name="id") Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<TourClub> tourClub = this.tourClubsRepo.findById(id);
        User user = this.userRepo.findByUsername(auth.getName());

        if(tourClub.isPresent()){
            Set<User> followers = tourClub.get().getFollowers();
            followers.add(user);
        }

        String refererUrl = httpServletRequest.getHeader("Referer");

        return "redirect:" + refererUrl;
    }

    @RequestMapping(value = "/clubs/{id}/unfollow", method = RequestMethod.GET)
    public String unfollowClub(HttpServletRequest httpServletRequest, @PathVariable(name = "id") Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<TourClub> tourClub = this.tourClubsRepo.findById(id);

        if(tourClub.isPresent()){
            Set<User> followers = tourClub.get().getFollowers();
            followers.removeIf(f -> f.getUsername().equals(auth.getName()));
        }

        String refererUrl = httpServletRequest.getHeader("Referer");

        return "redirect:" + refererUrl;
    }

}
