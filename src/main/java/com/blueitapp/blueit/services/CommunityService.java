package com.blueitapp.blueit.services;

import com.blueitapp.blueit.DTO.CommunityDTO;
import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.repositories.CommunityRepository;
import com.blueitapp.blueit.repositories.UserRepository;
import org.springframework.stereotype.Service;

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

        Community newCommunity = new Community();
        newCommunity.setName(communityName);

        communityRepository.save(newCommunity);

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
