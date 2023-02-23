package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional //executing within a transactional context. -> email/pass
public interface UserRepository extends CrudRepository<AppUser, UUID>{

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByEmailAndPassword(String email, String password);
}
