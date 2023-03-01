package com.blueitapp.blueit.services;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    UserRepository userRepository;
    public UserService(UserRepository repository){
        this.userRepository = repository;
    }
    //TODO: Add AUTH to verify user UUID w/ hashMap or get token from repository ID (Extract)

    //Create
    public void createUser(AppUser user) throws Exception {
        Optional<AppUser> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new Exception("Email already taken");
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setProfilePicture(user.getProfilePicture());
        // TODO add relationship
        userRepository.save(newUser);
    }
    //TODO: Compress files
    public void addProfilePicture(UUID userId, MultipartFile file) throws Exception {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new Exception("User not found");
        }
        AppUser user = userOptional.get();
        user.setImageName(file.getOriginalFilename());
        user.setImgType(file.getContentType());
        user.setProfilePicture(file.getBytes());
        userRepository.save(user);
    }

    //TODO: 1. You can use a hashmap to store the user's UUID and the token then use the token to verify the user's UUID
    //TODO: 2. You can use the repository ID to generate a token and use the token to verify the user's UUID

    //Read
    public Iterable<AppUser> getAllUsers(){
        return userRepository.findAll();
    }

    public AppUser getUserByEmailAndPassword(String email, String password) throws Exception{
        Optional<AppUser> user = userRepository.findByEmailAndPassword(email, password);
        if(user.isPresent()){
            return user.get();
        }
        throw new Exception("User not found");
    }

    public AppUser getUserById(UUID userId) throws Exception{
        Optional<AppUser> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new Exception("User not found");
    }

    public AppUser getUserByEmail(String email) throws Exception{
        Optional<AppUser> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        throw new Exception("User does not have an account");
    }

    //Update

    //Delete
}
