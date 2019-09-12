package com.example.tournetwork.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class TourClub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne
    private User owner;
    @ManyToMany
    private Set<User> followers = new HashSet<>();
    @OneToMany
    private Set<Post> posts;
    private String clubImage = "https://avatars.mds.yandex.net/get-pdb/1221543/989106be-0ada-477c-9aeb-2d1f30b8aaba/s1200?webp=false";
    private LocalDate clubBirthDate = LocalDate.now();
    private String about;

    public boolean isUserInFollowers(String username){

        Set<User> filteredUsers = this.followers.stream()
                .filter(u -> u.getUsername().equals(username))
                .collect(Collectors.toSet());

        return !filteredUsers.isEmpty();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public String getClubImage() {
        return clubImage;
    }

    public void setClubImage(String clubImage) {
        this.clubImage = clubImage;
    }

    public LocalDate getClubBirthDate() {
        return clubBirthDate;
    }

    public void setClubBirthDate(LocalDate clubBirthDate) {
        this.clubBirthDate = clubBirthDate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
