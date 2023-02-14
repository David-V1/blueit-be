package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
}
