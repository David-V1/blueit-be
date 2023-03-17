package com.blueitapp.blueit.services;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.models.UserCommunity;
import com.blueitapp.blueit.repositories.CommunityRepository;
import com.blueitapp.blueit.repositories.UserCommunityRepository;
import com.blueitapp.blueit.repositories.UserRepository;
import com.blueitapp.blueit.utils.DateStampUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserCommunityService {

    private final UserCommunityRepository userCommunityRepository;

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    public UserCommunityService(UserCommunityRepository userCommunityRepository, CommunityService communityService, UserService userService, CommunityRepository communityRepository, UserRepository userRepository) {
        this.userCommunityRepository = userCommunityRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    // Create

    public void joinCommunity(UUID userId, Long communityId) throws Exception {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        // Check if user exists
        if (userOptional.isEmpty())
            throw new Exception("User does not exist");
        AppUser user = userOptional.get();

        // Check if community exists
        Optional<Community> communityOptional = communityRepository.findById(communityId);
        if (communityOptional.isEmpty())
            throw new Exception("Community does not exist");
        Community community = communityOptional.get();

        // Check if user is already in community
        Optional<UserCommunity> userCommunityOptional = userCommunityRepository.findByUserIdAndCommunityId(userId, communityId);
        if (userCommunityOptional.isPresent())
            throw new Exception("User is already in community");

        // Add user to community
        UserCommunity userCommunity = new UserCommunity();
        userCommunity.setCommunity(community);
        userCommunity.setUser(user);
        userCommunity.setDateJoined(DateStampUtil.setDateTimeStamp());
        userCommunityRepository.save(userCommunity);

    }

    // Read
    public Iterable<UserCommunity> getAllUserCommunities() {
        return userCommunityRepository.findAll();
    }

    public Iterable<UserCommunity> getAllUserCommunitiesByUserId(UUID userId) throws Exception {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty())
            throw new Exception("User does not exist");

        return userCommunityRepository.findByUserId(userId);
    }

    public Iterable<UserCommunity> getAllCommunitiesByCommunityId(Long communityId) throws Exception {
        Optional<Community> communityOptional = communityRepository.findById(communityId);
        if (communityOptional.isEmpty())
            throw new Exception("Community does not exist");

        List<UserCommunity> userCommunities = new ArrayList<>();
        userCommunityRepository.findAll().forEach(userCommunities::add);
        return userCommunities.stream()
                .filter(userCommunity -> userCommunity.getCommunity().getId().equals(communityId))
                .collect(Collectors.toList());


    }

    public int getNumberOfMembersByCommunityId(Long communityId) throws Exception {
        Optional<Community> communityOptional = communityRepository.findById(communityId);
        if (communityOptional.isEmpty())
            throw new Exception("Community does not exist");

        List<UserCommunity> userCommunities = new ArrayList<>();
        userCommunityRepository.findAll().forEach(userCommunities::add);
        return (int) userCommunities.stream()
                .filter(userCommunity -> userCommunity.getCommunity().getId().equals(communityId))
                .count();
    }

    // Update

    // Delete
}
