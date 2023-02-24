package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Comment;
import com.blueitapp.blueit.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<Comment> findAllByUserId(UUID userId);
}
