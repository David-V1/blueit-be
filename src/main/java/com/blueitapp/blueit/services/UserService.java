package com.blueitapp.blueit.services;

import com.blueitapp.blueit.DTO.UserDTO;
import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository repository;
    public UserService(UserRepository repository){
        this.repository = repository;
    }
    //TODO: Add AUTH to verify user UUID w/ hashMap or get token from repository ID (Extract)


    //Create
    public void createUser(AppUser user) throws Exception {
        Optional<AppUser> userOptional = repository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new Exception("Email already taken");
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setProfilePicture(user.getProfilePicture());
        // TODO add relationship
        repository.save(newUser);
    }

    //TODO: 1. You can use a hashmap to store the user's UUID and the token then use the token to verify the user's UUID
    //TODO: 2. You can use the repository ID to generate a token and use the token to verify the user's UUID
    //Read
    public Iterable<AppUser> getAllUsers(){
        return repository.findAll();
    }

    public AppUser getUserByEmailAndPassword(String email, String password) throws Exception{
        Optional<AppUser> user = repository.findByEmailAndPassword(email, password);
        if(user.isPresent()){
            return user.get();
        }
        throw new Exception("User not found");
    }

    //Update

    //Delete
}
