package com.blueitapp.blueit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "blueit_communities")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String dateCreated;
    @Column(name="description", length = 500)
    private String description;

    @Column(name = "admin")
    private UUID admin; // initial creator
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval =  true)
    @JoinColumn(name= "image_id",referencedColumnName = "image_id")
    private Image logo;
    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post;
    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCommunity> userCommunity;

    public Community(Long id, String name, String dateCreated, String description, UUID admin, Image logo, List<Post> post) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.description = description;
        this.admin = admin;
        this.logo = logo;
        this.post = post;
    }

    public Community() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getAdmin() {
        return admin;
    }

    public void setAdmin(UUID admin) {
        this.admin = admin;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }



    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }


}
