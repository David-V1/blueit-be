package com.blueitapp.blueit.services;

import com.blueitapp.blueit.DTO.CommunityDTO;
import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.models.Image;
import com.blueitapp.blueit.repositories.CommunityRepository;
import com.blueitapp.blueit.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.blueitapp.blueit.utils.ImageUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityService(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;

        this.userRepository = userRepository;
    }

    // Create
    public void createCommunity(CommunityDTO community) throws Exception {
        String communityName = community.name.trim().toLowerCase().replaceAll("\\s", "");

        if (communityName.trim().isEmpty()) {
            throw new Exception("Community name cannot be empty!");
        }

        Optional<Community> communityOptional = communityRepository.findByName(communityName);
        if(communityOptional.isPresent()){
            throw new Exception("Community already exists");
        }
        // Date formatter
        LocalDateTime newDate = LocalDateTime.now();
        DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCreated = newDate.format(myDateFormat);

        Community newCommunity = new Community();
        newCommunity.setName(communityName);
        newCommunity.setDateCreated(dateCreated);

        communityRepository.save(newCommunity);
    }

    public void addDescription(String description, Long communityId) throws Exception {
        Optional<Community> communityOptional = communityRepository.findById(communityId);
        if (communityOptional.isEmpty()){
            throw new Exception("No community found");
        }

        description.trim();
        if (description.equals(""))
            throw new Exception();

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
