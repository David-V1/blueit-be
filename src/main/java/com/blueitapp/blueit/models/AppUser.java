package com.blueitapp.blueit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
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

    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_type")
    private String imgType;
    @Lob
    @Column(name = "profile_picture", length = 10000)
    private byte[] profilePicture;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post;
    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostVotes> likes;


    public AppUser(UUID id, String username, String password, String email, String imageName, String imgType, byte[] profilePicture, List<Post> post) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageName = imageName;
        this.imgType = imgType;
        this.profilePicture = profilePicture;
        this.post = post;
    }

    public AppUser() { }

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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public List<PostVotes> getLikes() {
        return likes;
    }

    public void setLikes(List<PostVotes> likes) {
        this.likes = likes;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}