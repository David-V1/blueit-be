package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findByName(String name);

}
