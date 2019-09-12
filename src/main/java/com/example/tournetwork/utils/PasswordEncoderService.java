package com.example.tournetwork.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class PasswordEncoderService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    PasswordEncoderService(){
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String getHashedPassword(String password){
        return this.bCryptPasswordEncoder.encode(password);
    }


}
