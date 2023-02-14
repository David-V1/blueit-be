package com.blueitapp.blueit.services;

import com.blueitapp.blueit.DTO.PostDTO;
import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Image;
import com.blueitapp.blueit.models.Post;
import com.blueitapp.blueit.repositories.PostRepository;
import com.blueitapp.blueit.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService( PostRepository repository, UserRepository userRepository) {
        this.postRepository = repository;
        this.userRepository = userRepository;
    }

    //CREATE
    // We are going to build our images and store them in a set of images.
    public Set<Image> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<Image> imageModels = new HashSet<>();
        // building Objects of our Image-model to save in Set {}.
        for (MultipartFile file : multipartFiles) {
            Image imageModel = new Image(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }

    public void createPost(UUID userId, PostDTO post, MultipartFile[] file) throws Exception {

        Optional<AppUser> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new Exception("User not found");
        }
        AppUser user = userOptional.get();
        Post newPost = new Post(); // create new post instance for repository.
        Set<Image> images = uploadImage(file); // process images.

        newPost.setTitle(post.title);
        newPost.setLikes(post.likes);
        newPost.setPostedDate((Date) post.postedDate);
        newPost.setContent(post.content);
        newPost.setPostImages(images); //setting our images to our Post-model OneToMany rel w/ Image-Model
        newPost.setUser(user);
        postRepository.save(newPost);
    }

    //READ
    public Iterable<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public Post getPostByTitle(String title) throws Exception{
        Optional<Post> post = postRepository.findByTitle(title);
        if(post.isPresent()){
            return post.get();
        }
        throw new Exception("Post not found");
    }

    public Post getPostById(Long id) throws Exception{
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        }
        throw new Exception("Post not found");
    }

    //UPDATE

    //DELETE
}
