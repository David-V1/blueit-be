package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Post;
import com.blueitapp.blueit.models.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Votes, Long> {
    Optional<Votes> findByUserIdAndPostId(AppUser userId, Post postId);
    Optional<Votes> findByUserId(AppUser userId);
}
