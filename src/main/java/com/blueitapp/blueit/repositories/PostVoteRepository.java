package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Comment;
import com.blueitapp.blueit.models.Post;
import com.blueitapp.blueit.models.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    Optional<PostVote> findByUserIdAndPostId(AppUser userId, Post postId);
}
