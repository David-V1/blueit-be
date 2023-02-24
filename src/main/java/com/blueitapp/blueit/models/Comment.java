package com.blueitapp.blueit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "date_created")
    private String dateCreated;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser userId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

    @JsonIgnore
    @OneToMany(mappedBy = "commentId", cascade = CascadeType.ALL)
    @Column(name = "votes")
    private Set<Votes> votes;

    public Comment() {
    }

    public Comment(String commentText, String dateCreated, AppUser userId, Post postId, Set<Votes> votes) {
        this.commentText = commentText;
        this.dateCreated = dateCreated;
        this.userId = userId;
        this.postId = postId;
        this.votes = votes;
    }
}
