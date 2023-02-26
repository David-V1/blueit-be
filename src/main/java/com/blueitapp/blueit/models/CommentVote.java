package com.blueitapp.blueit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "comment_votes")
public class CommentVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;
    @Column(name = "date_voted")
    private LocalDateTime date;
    @Column(name = "vote_type")
    private String commentVoteType; // true = upvote, false = downvote
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser userId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comment comment;

    public CommentVote(){}
}
