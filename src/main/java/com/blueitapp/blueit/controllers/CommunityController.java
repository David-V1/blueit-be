package com.blueitapp.blueit.controllers;

import com.blueitapp.blueit.DTO.CommunityDTO;
import com.blueitapp.blueit.models.Community;
import com.blueitapp.blueit.services.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public void createCommunity(@RequestBody CommunityDTO community){
        try {
            service.createCommunity(community);
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
    @GetMapping("/{id}")
    public Community getCommunityById(@PathVariable Long id){
        try {
            return service.getCommunityById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Community id not found", e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}")
    public Community getCommunityByName(@PathVariable String name){
        try {
            return service.getCommunityByName(name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Community name not found", e);
        }
    }
    // Update

    // Delete
}
