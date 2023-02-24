package com.blueitapp.blueit.services;

import com.blueitapp.blueit.models.AppUser;
import com.blueitapp.blueit.models.Comment;
import com.blueitapp.blueit.models.Post;
import com.blueitapp.blueit.repositories.CommentRepository;
import com.blueitapp.blueit.repositories.PostRepository;
import com.blueitapp.blueit.repositories.UserRepository;
import com.blueitapp.blueit.repositories.VoteRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, VoteRepository commentVoteRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.voteRepository = commentVoteRepository;
    }

    //Create
    public void addComment(Comment commentText, Long postId, UUID userId) throws Exception {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new Exception("Post not found");
        }
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        // Date formatter
        LocalDateTime postDate = LocalDateTime.now();
        DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String newPostDate = postDate.format(myDateFormat);


        Post post = postOptional.get();
        AppUser user = userOptional.get();
        Comment comment = new Comment();

        comment.setCommentText(commentText.getCommentText());
        comment.setPostId(post);
        comment.setUserId(user);
        comment.setDateCreated(newPostDate);
        commentRepository.save(comment);
    }

    //Read
    public Iterable<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new Exception("Comment not found");
        }
        return commentOptional.get();
    }

    public Iterable<Comment> getAllCommentsByUserId(UUID userId) throws Exception {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }
        AppUser user = userOptional.get();
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);
        return comments.stream()
                .filter(comment -> comment.getUserId().equals(user))
                .collect(Collectors.toList());
    }

    public Iterable<Comment> getAllCommentsByPostId(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            System.out.println("Post not found");
            return null;
        }
        Post post = postOptional.get();
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);
        return comments.stream()
                .filter(comment -> comment.getPostId().equals(post))
                .collect(Collectors.toList());

    }


    //Update

    //Delete
}
