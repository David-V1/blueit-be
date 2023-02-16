package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.Community;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends CrudRepository<Community, Long> {
    Optional<Community> findByName(String name);

}
