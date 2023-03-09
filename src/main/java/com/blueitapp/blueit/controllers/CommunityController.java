package com.blueitapp.blueit.controllers;

import com.blueitapp.blueit.DTO.CommunityDTO;
import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.services.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/b")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityController {

    private final CommunityService service;
    public CommunityController(CommunityService communityService){
        this.service = communityService;
    }

    // Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public void createCommunity(@RequestBody CommunityDTO community) {
        try {
            service.createCommunity(community);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/description/{description}/id/{communityId}")
    public void addCommunityDescription(@PathVariable String description, @PathVariable Long communityId) {
        try {
            service.addDescription(description, communityId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/upload/{communityId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void addCommunityLogo(@PathVariable Long communityId, @RequestPart("imageFile") MultipartFile file) {
        try {
            service.addCommunityLogo(communityId, file);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    // Read
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Iterable<Community> getAllCommunities(){
        return service.getAllCommunities();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comId/{id}")
    public Community getCommunityById(@PathVariable Long id){
        try {
            return service.getCommunityById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Community id not found", e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}")
    public Community getCommunityByName(@PathVariable String name) {
        try {
            return service.getCommunityByName(name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Community name not found", e);
        }
    }
    // Update

    // Delete
}
