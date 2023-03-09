package com.blueitapp.blueit.services;

import com.blueitapp.blueit.models.*;
import com.blueitapp.blueit.repositories.*;
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
    private final CommentVoteRepository commentVoteRepository;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository,
            CommentVoteRepository commentVoteRepository) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentVoteRepository = commentVoteRepository;
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
        comment.setPost(post);
        comment.setUser(user);
        comment.setDate(newPostDate);
        comment.setLikes(0);
        commentRepository.save(comment);
    }

    public void addVote(UUID userId, Long commentId, String voteType) throws Exception{
        //1 vote per user per comment
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            throw new Exception("Comment not found");
        }
        Optional<CommentVote> commentVoteOptional = commentVoteRepository.findByUserIdAndComment(userOptional.get(), commentOptional.get());
        if (commentVoteOptional.isPresent()) {
            updateCommentVote(commentVoteOptional.get(), voteType);
            return;
        }
        // new vote
        CommentVote commentVote = new CommentVote();
        commentVote.setComment(commentOptional.get());
        commentVote.setUserId(userOptional.get());
        commentVote.setCommentVoteType(voteType);
        commentVote.setDate(LocalDateTime.now());

        // upVote/downVote
        Comment comment = commentOptional.get();
        if (voteType.equals("true")) {
            if (comment.getLikes() == null) {
                comment.setLikes(1);
            } else {
                comment.setLikes(comment.getLikes() + 1);
                commentVote.setCommentVoteType("true");
            }
        } else {
            if (comment.getLikes() == null) {
                comment.setLikes(0);
            } else {
                comment.setLikes(comment.getLikes() - 1);
                commentVote.setCommentVoteType("false");
            }
        }
        commentVoteRepository.save(commentVote);

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
                .filter(comment -> comment.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public Iterable<Comment> getAllCommentsByPostId(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            return null;
        }
        Post post = postOptional.get();
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);
        return comments.stream()
                .filter(comment -> comment.getPost().equals(post))
                .collect(Collectors.toList());

    }


    //Update
    public void updateCommentVote(CommentVote commentVote, String voteType) throws Exception {
        //relational comment id to update
        Optional<Comment> commentOptional = commentRepository.findById(commentVote.getComment().getId());
        if (commentOptional.isEmpty()) {
            throw new Exception("Comment not found");
        }
        Comment comment = commentOptional.get();
        //check type voted
        if (commentVote.getCommentVoteType().equals(voteType)) {
            return;
        }
        if (commentVote.getCommentVoteType().equals("true") && voteType.equals("false")) {
            comment.setLikes(comment.getLikes() - 1);
            commentRepository.save(comment);
        } else {
            comment.setLikes(comment.getLikes() + 1);
            commentRepository.save(comment);
        }
        commentVote.setCommentVoteType(voteType);
        commentVoteRepository.save(commentVote);

    }
    //Delete
    // Foreign key ERROR, might need to delete CommentVote's comment_id first in order to delete a comment.
}
