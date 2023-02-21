package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Post;
import com.blueitapp.blueit.models.PostVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostVotesReporitory extends JpaRepository<PostVotes, Long> {
    Optional<PostVotes> findByUserIdAndPostId(AppUser userId, Post postId);
    Optional<PostVotes> findByUserId(AppUser userId);
}
