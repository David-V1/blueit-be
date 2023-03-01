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

    @Column(name = "comment_text", length = 10000)
    private String commentText;

    @Column(name = "date_created")
    private String date;

    @Column(name = "likes")
    private Integer likes;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @Column(name = "votes")
    private Set<CommentVote> commentVote;

    public Comment() {
    }

    public Comment(String commentText, String date, Integer likes, AppUser user, Post post, Set<CommentVote> commentVote) {
        this.commentText = commentText;
        this.date = date;
        this.likes = likes;
        this.user = user;
        this.post = post;
        this.commentVote = commentVote;
    }
}
