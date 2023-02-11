package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<AppUser, UUID>{

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByEmailAndPassword(String email, String password);
}
