package com.blueitapp.blueit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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
    private String postedDate;
    @Column(name="description")
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "image_id",referencedColumnName = "image_id")
    private Image logo;
    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post;

    @ManyToMany
    @JoinTable(
            name = "user_community",
            joinColumns = @JoinColumn( name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> members;

    public Community(Long id, String name, String postedDate, String description, Image logo, List<Post> post, List<AppUser> members) {
        this.id = id;
        this.name = name;
        this.postedDate = postedDate;
        this.description = description;
        this.logo = logo;
        this.post = post;
        this.members = members;
    }

    public Community(String name) {
        this.name = name;
        this.post = post;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Community() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public List<AppUser> getUsers() {
        return members;
    }

    public void setUsers(List<AppUser> users) {
        this.members = users;
    }
}
