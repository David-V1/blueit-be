package com.blueitapp.blueit.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @Lob
    @Column(name = "profile_picture", length = 10000)
    private String profilePicture;

//    private List<Post> post;
    public AppUser(UUID id, String username, String password, String email, String profilePicture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profilePicture = profilePicture;

        }
    public AppUser() {}

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}