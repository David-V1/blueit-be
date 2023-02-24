package com.blueitapp.blueit.controllers;

import com.blueitapp.blueit.models.Comment;
import com.blueitapp.blueit.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post/{postId}/user/{userId}")
    public void addComment(@RequestBody Comment commentText, @PathVariable Long postId, @PathVariable UUID userId) {
        try {
            commentService.addComment(commentText, postId, userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    //Read
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Iterable<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        try {
            return commentService.getCommentById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{userId}")
    public Iterable<Comment> getCommentsByUserId(@PathVariable UUID userId) {
        try {
            return commentService.getAllCommentsByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post/{postId}")
    public Iterable<Comment> getCommentsByPostId(@PathVariable Long postId) {
        try {
            return commentService.getAllCommentsByPostId(postId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    //Update

    //Delete

}
