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
@Table(name = "Post_Votes")
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;
    @Column(name = "date_voted")
    private LocalDateTime dateVoted;
    @Column(name = "post_vote") // true = upvote, false = downvote
    private String postVoteType; //TODO: change to boolean
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser userId;
    @ManyToOne(optional = true)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;


}

