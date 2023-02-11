package com.blueitapp.blueit.controllers;

import com.blueitapp.blueit.DTO.UserDTO;
import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
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

    // Update

    // Delete
}
