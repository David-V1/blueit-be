package com.blueitapp.blueit.services;

import com.blueitapp.blueit.DTO.CommunityDTO;
import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.models.Image;
import com.blueitapp.blueit.models.UserCommunity;
import com.blueitapp.blueit.repositories.CommunityRepository;
import com.blueitapp.blueit.repositories.UserCommunityRepository;
import com.blueitapp.blueit.repositories.UserRepository;
import com.blueitapp.blueit.utils.DateStampUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.blueitapp.blueit.utils.ImageUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final UserCommunityRepository userCommunityRepository;
    public CommunityService(CommunityRepository communityRepository, UserRepository userRepository, UserCommunityRepository userCommunityRepository) {
        this.communityRepository = communityRepository;

        this.userRepository = userRepository;

        this.userCommunityRepository = userCommunityRepository;
    }

    // Create
    public void createCommunity(CommunityDTO community, UUID admin) throws Exception {
        Optional<AppUser> userOptional = userRepository.findById(admin);
        if (userOptional.isEmpty())
            throw new Exception("Something went wrong assigning admin");

        AppUser user = userOptional.get();

        String communityName = community.name.trim().toLowerCase().replaceAll("\\s", "");

        Optional<Community> communityOptional = communityRepository.findByName(communityName);
        if(communityOptional.isPresent()){
            throw new Exception("Community already exists");
        }

        if (communityName.trim().isEmpty()) {
            throw new Exception("Community name cannot be empty!");
        }

        // Date formatter
        String dateCreated = DateStampUtil.setDateTimeStamp();

        Community newCommunity = new Community();
        newCommunity.setName(communityName);
        newCommunity.setDateCreated(dateCreated);
        newCommunity.setAdmin(userOptional.get().getId());

        UserCommunity userCommunity = new UserCommunity();
        userCommunity.setCommunity(newCommunity);
        userCommunity.setUser(user);
        userCommunity.setDateJoined(dateCreated);

        communityRepository.save(newCommunity);
        userCommunityRepository.save(userCommunity);
    }

    public void addDescription(Long communityId, String description) throws Exception {
        Optional<Community> communityOptional = communityRepository.findById(communityId);
        if (communityOptional.isEmpty()){
            throw new Exception("No community found");
        }

        description.trim();
        if (description.equals(""))
            throw new Exception(" Description empty ");

        Community community = communityOptional.get();
        community.setDescription(description);
        communityRepository.save(community);
    }

    //TODO: Need to compress image
    //TODO: Need to check if there is an image already for the Community, delete if so, add new...
    public void addCommunityLogo(Long communityId, MultipartFile file) throws Exception {
        Optional<Community> communityOptional = communityRepository.findById(communityId);
        if (communityOptional.isEmpty()) {
            throw new Exception("Community not found!");
        }
        Community community = communityOptional.get();
        if (community.getLogo() != null) {
            community.setLogo(null);
            communityRepository.save(community);
        }

        Image image = ImageUtils.uploadImage(file);
        community.setLogo(image);
        communityRepository.save(community);
    }

    // Read
    public Iterable<Community> getAllCommunities(){
        return communityRepository.findAll();
    }

    public Community getCommunityById(Long id) throws Exception {
        Optional<Community> communityOptional = communityRepository.findById(id);
        if (communityOptional.isEmpty()) {
            throw new Exception("Community not found");
        }

        return communityOptional.get();
    }

    public Community getCommunityByName(String name) throws Exception{
        Optional<Community> communityOptional = communityRepository.findByName(name);
        if (communityOptional.isEmpty()){
            throw new Exception("Community not found");
        }

        return communityOptional.get();
    }

    // Update

    // Delete



}
