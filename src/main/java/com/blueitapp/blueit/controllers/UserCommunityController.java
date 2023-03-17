package com.blueitapp.blueit.controllers;

import com.blueitapp.blueit.models.UserCommunity;
import com.blueitapp.blueit.services.UserCommunityService;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("api/u_community")
@CrossOrigin(origins = "http://localhost:4200")
public class UserCommunityController {

    private final UserCommunityService userCommunityService;

    public UserCommunityController(UserCommunityService userCommunityService) {
        this.userCommunityService = userCommunityService;
    }

    // Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join_community/u/{userId}/b/{communityId}")
    public void joinCommunity(@PathVariable UUID userId, @PathVariable Long communityId) {
        try {
        userCommunityService.joinCommunity(userId, communityId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Read
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Iterable<UserCommunity> getAllUserCommunities() {
        return userCommunityService.getAllUserCommunities();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userId/{userId}")
    public Iterable<UserCommunity> getUserCommunitiesByUserId(@PathVariable UUID userId) {
        try {
            return userCommunityService.getAllUserCommunitiesByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/communityId/{communityId}")
    public Iterable<UserCommunity> getAllUserCommunitiesByCommunityId(@PathVariable Long communityId) {
        try {
            return userCommunityService.getAllCommunitiesByCommunityId(communityId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("members/{communityId}")
    public int getNumOfMembers(@PathVariable Long communityId) {
        try {
            return userCommunityService.getNumberOfMembersByCommunityId(communityId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    // Update

    // Delete

}
