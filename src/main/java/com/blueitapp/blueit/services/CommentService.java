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


}
