package com.blueitapp.blueit.repositories;

import com.blueitapp.blueit.models.UserCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCommunityRepository extends JpaRepository<UserCommunity, Long> {
    List<UserCommunity> findByCommunityId(Long communityId);
}
