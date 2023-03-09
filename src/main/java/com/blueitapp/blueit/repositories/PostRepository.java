package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Post> findByTitle(String title);

    Optional<Post> findByUser_Id(UUID id);
    Iterable<Post> findAllByUserId(UUID userId);
    List<Post> findPostByCommunity_Id(Long id);
}
