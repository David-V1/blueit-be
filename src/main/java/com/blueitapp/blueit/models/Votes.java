package com.blueitapp.blueit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_votes")
public class Votes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;
    @Column(name = "date_voted")
    private LocalDateTime dateVoted;
    @Column(name = "vote_type") // true = upvote, false = downvote
    private String voteType;
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser userId;
    @ManyToOne(optional = true)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comment commentId;

}

