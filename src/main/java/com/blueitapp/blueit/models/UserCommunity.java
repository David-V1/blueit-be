package com.blueitapp.blueit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_community")
public class UserCommunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "community_id", referencedColumnName = "id")
    private Community community;

    @Column(name = "date_joined")
    private String dateJoined;

    public UserCommunity(AppUser user, Community community) {
        this.user = user;
        this.community = community;
    }

    public UserCommunity() {}

}
