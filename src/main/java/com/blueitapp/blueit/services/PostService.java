package com.blueitapp.blueit.services;

import com.blueitapp.blueit.DTO.PostDTO;
import com.blueitapp.blueit.models.*;
import com.blueitapp.blueit.repositories.CommunityRepository;
import com.blueitapp.blueit.repositories.PostRepository;
import com.blueitapp.blueit.repositories.PostVoteRepository;
import com.blueitapp.blueit.repositories.UserRepository;
import com.blueitapp.blueit.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
//    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final PostVoteRepository postVoteRepository;
    public PostService( PostRepository repository, UserRepository userRepository, CommunityRepository communityRepository, PostVoteRepository postVoteRepository) {
        this.postRepository = repository;
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
        this.postVoteRepository = postVoteRepository;
    }

    //CREATE

    //TODO: Edge case. Handle if user doesn't upload any images.
    public void createPost(UUID userId, String community, PostDTO post, MultipartFile[] file) throws Exception {
        // USER checks
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new Exception("User not found");
        }
        // COMMUNITY checks
        if (community.equals(""))
            throw new Exception("Community can't be empty");

        String communityName = community.toLowerCase();
        Optional<Community> communityOptional = communityRepository.findByName(communityName);
        if(communityOptional.isEmpty()){
            throw new Exception("Community not found");
        }

        // Date formatter
        LocalDateTime postDate = LocalDateTime.now();
        DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String newPostDate = postDate.format(myDateFormat);

        Community community1 = communityOptional.get();
        AppUser user = userOptional.get();
        Post newPost = new Post(); // create new post instance for repository.
        Set<Image> images = ImageUtils.uploadImageArr(file); // process images.

        newPost.setTitle(post.title);
        newPost.setPostedDate(newPostDate);
        newPost.setContent(post.content);
        newPost.setPostImages(images); //setting our images to our Post-model OneToMany rel w/ Image-Model
        newPost.setUser(user);
        newPost.setCommunity(community1);
        newPost.setVotes(0);
        postRepository.save(newPost);
    }

    //TODO: Need to handle down votes as well.
    public void votePost(UUID userId, Long postId, String voteType) throws Exception {

        //Ensuring there is 1 vote per user per post
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new Exception("User not found");
        }
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isEmpty()){
            throw new Exception("Post not found");
        }
        Optional<PostVote> postVotesOptional = postVoteRepository.findByUserIdAndPostId(userOptional.get(), postOptional.get());
        if (postVotesOptional.isPresent()){
            updateVote(postVotesOptional.get(), voteType);
            return;
        }

        //Creating new vote
        PostVote newVote = new PostVote();
        newVote.setUserId(userOptional.get());
        newVote.setPostId(postOptional.get());
        newVote.setPostVoteType(voteType);
        newVote.setDateVoted(LocalDateTime.now());

        // Post Vote logic
        Post post = postOptional.get();
        if (voteType.equals("true")) {
            post.setVotes(post.getVotes() + 1);
            newVote.setPostVoteType("true");
        } else {
            post.setVotes(post.getVotes() - 1);
            newVote.setPostVoteType("false");
        }
        postVoteRepository.save(newVote);
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
        throw new Exception("Posts not found");
    }

    public Post getPostById(Long id) throws Exception {
        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent()) {
            return post.get();
        }
        throw new Exception("Post not found");
    }

    public Integer getPostLikes(Long postId) throws Exception{
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isEmpty()){
            throw new Exception("Post not found");
        }
        return postOptional.get().getVotes();
    }

    //UPDATE
    public void updateVote(PostVote postVote, String voteType) throws Exception {
        Optional<Post> postOptional = postRepository.findById(postVote.getPostId().getId());
        if(postOptional.isEmpty()){
            throw new Exception("Post not found. Something went wrong updating.");
        }
        if (postVote.getPostVoteType().equals(voteType)){
            return;
        }
        if (postVote.getPostVoteType().equals("true") && voteType.equals("false")){
            postOptional.get().setVotes(postOptional.get().getVotes() - 1);
            postRepository.save(postOptional.get());
        } else {
            postOptional.get().setVotes(postOptional.get().getVotes() + 1);
            postRepository.save(postOptional.get());
        }
        postVote.setPostVoteType(voteType);
        postVoteRepository.save(postVote);

        }

    }

    //DELETE
