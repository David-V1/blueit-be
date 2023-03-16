package com.blueitapp.blueit.controllers;

import com.blueitapp.blueit.DTO.CommunityDTO;
import com.blueitapp.blueit.DTO.PostDTO;
import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Image;
import com.blueitapp.blueit.models.Post;
import com.blueitapp.blueit.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService service;
    public PostController(PostService postService){
        this.service = postService;
    }

    //CREATE
    //We need a MULTIPART request to upload images, so removing @RequestBody & adding @RequestPart.
    // We can denote this endpoint as a MULTIPART
    // Inside the parameters we specify an array for potential multiple images w/ MultipartFile[].
    // Now we need to prepare this file for processing, so we can save it  to our DB (inside service).
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = {"/create/{userId}/b/{community}"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createPost(
            @PathVariable("userId") UUID userId,
            @PathVariable("community") String community,
            @RequestPart("post") PostDTO post,
            @RequestPart("imageFile") MultipartFile[] file){
        try {
            service.createPost(userId, community, post, file);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value ="/vote/{userId}/{postId}/{voteType}")
    public void votePost(@PathVariable UUID userId, @PathVariable Long postId, @PathVariable String voteType){
        try {

            service.votePost(userId, postId, voteType);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    //READ
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Iterable<Post> getAllPosts(){
        return service.getAllPosts();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{title}")
    public Post getPostByTitle(@PathVariable String title){
        try {
            return service.getPostByTitle(title);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/id/{id}")
    public Post getPostById(@PathVariable Long id){
        try {
            return service.getPostById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/likes/{postId}")
    public Integer getPostLikes(@PathVariable Long postId){
        try {
            return service.getPostLikes(postId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userId/{user}")
    public Iterable<Post> getAllPostByUser(@PathVariable UUID user) {
        System.out.println(user);
        try {
            return service.getAllPostByUserId(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE

    //DELETE
}
