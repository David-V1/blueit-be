package com.blueitapp.blueit.controllers;


import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin()
public class UserController {
    private final UserService service;
    public UserController(UserService userService){
        this.service = userService;
    }

    // Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public void createUser(@RequestBody AppUser user){
        try {
            service.createUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = {"/{userId}"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void addProfilePic(@PathVariable UUID userId, @RequestPart("imageFile") MultipartFile file){
        try {
            service.addProfilePicture(userId, file);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    // Read
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Iterable<AppUser> getAllUsers(){
        return service.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email/{email}/pass/{password}")
    public AppUser getUserByEmailAndPassword(@PathVariable String email, @PathVariable String password){
        try {
            return service.getUserByEmailAndPassword(email, password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/u/id/{userid}")
    public AppUser getUserById(@PathVariable UUID userid){
        try {
            return service.getUserById(userid);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/u/email/{email}")
    public AppUser getUserByEmail(@PathVariable String email){
        try {
            return service.getUserByEmail(email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // Update


    // Delete
}
