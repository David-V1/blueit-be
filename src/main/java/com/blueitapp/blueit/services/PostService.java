package com.blueitapp.blueit.services;

import com.blueitapp.blueit.models.Image;
import com.blueitapp.blueit.models.Post;
import com.blueitapp.blueit.repositories.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {
    private final PostRepository repository;
    public PostService( PostRepository repository) {
        this.repository = repository;
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

    public void createPost(Post post, MultipartFile[] file) throws Exception {

//        Optional<Post> postOptional = repository.findById(post.getId());
//        if(postOptional.isPresent()){
//            throw new Exception("Post already exists");
//        }

        Post newPost = new Post(); // create new post instance for repository.
        Set<Image> images = uploadImage(file); // process images.

        newPost.setTitle(post.getTitle());
        newPost.setLikes(post.getLikes());
        newPost.setPostedDate(post.getPostedDate());
        newPost.setContent(post.getContent());
        newPost.setPostImages(images); //setting our images to our Post-model OneToMany rel w/ Image-Model
        repository.save(newPost);
    }

    //READ
    public Iterable<Post> getAllPosts(){
        return repository.findAll();
    }
    public Post getPostByTitle(String title) throws Exception{
        Optional<Post> post = repository.findByTitle(title);
        if(post.isPresent()){
            return post.get();
        }
        throw new Exception("Post not found");
    }

    public Post getPostById(Long id) throws Exception{
        Optional<Post> post = repository.findById(id);
        if(post.isPresent()){
            return post.get();
        }
        throw new Exception("Post not found");
    }

    //UPDATE

    //DELETE
}
