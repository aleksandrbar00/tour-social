package com.example.tournetwork;

import com.example.tournetwork.model.User;
import com.example.tournetwork.repositories.UserRepo;
import com.example.tournetwork.utils.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class MainController {

    private PasswordEncoderService passwordEncoderService;
    private UserRepo userRepo;

    @Autowired
    MainController(PasswordEncoderService passwordEncoderService, UserRepo userRepo){
        this.passwordEncoderService = passwordEncoderService;
        this.userRepo = userRepo;
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String getUserPage(@PathVariable("username") String username, Model model){
        User user = this.userRepo.findByUsername(username);
        model.addAttribute("user", user);
        return "user-page";
    }

    @RequestMapping(value = "/user/{username}/settings", method = RequestMethod.GET)
    public String getUserSettings(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userRepo.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "user-settings";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewMainPage(Model model){
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(authentication instanceof UserDetails){
            UserDetails user = ((UserDetails) authentication);
            model.addAttribute("user", user);
            return "main";
        }
        model.addAttribute("user", authentication);
        return "main";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String registrationPage(){
        return "registration";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String createUser(@RequestParam MultiValueMap body, Model model) throws Exception{
        User dbUser = this.userRepo.findByUsername((String) body.getFirst("username"));

        if(dbUser != null){
            model.addAttribute("error", "This username is already in use!");
            return "registration";
        }else{
            System.out.println(body);
            User user = new User();
            user.setUsername((String) body.getFirst("username"));
            user.setEmail((String) body.getFirst("email"));
            user.setFirstName((String) body.getFirst("firstName"));
            user.setLastName((String) body.getFirst("lastName"));
            String hashedPassword = this.passwordEncoderService.getHashedPassword((String) body.getFirst("password"));
            user.setPassword(hashedPassword);
            this.userRepo.save(user);
            return "redirect:/login";
        }
    }
}
