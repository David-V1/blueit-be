package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.models.UserCommunity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCommunityRepository extends JpaRepository<UserCommunity, Long> {

    List<UserCommunity> findByUserId(UUID userId);


    Optional<UserCommunity> findByUserIdAndCommunityId(UUID userId, Long communityId);
}
