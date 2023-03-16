package com.blueitapp.blueit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_type")
    private String imgType;
    @Lob
    @Basic(fetch=FetchType.LAZY, optional=true)
    @Column(name = "profile_picture", length = 10000)
    private byte[] profilePicture;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post;
    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostVote> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentVote> commentVotes;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCommunity> userCommunities;

    public AppUser(UUID id, String username, String password, String email, String imageName, String imgType, byte[] profilePicture, List<Post> post, List<PostVote> likes, List<Comment> comments, List<CommentVote> commentVotes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageName = imageName;
        this.imgType = imgType;
        this.profilePicture = profilePicture;
        this.post = post;
        this.likes = likes;
        this.comments = comments;
        this.commentVotes = commentVotes;

    }

    public AppUser() { }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public List<PostVote> getLikes() {
        return likes;
    }

    public void setLikes(List<PostVote> likes) {
        this.likes = likes;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<CommentVote> getCommentVotes() {
        return commentVotes;
    }

    public void setCommentVotes(List<CommentVote> commentVotes) {
        this.commentVotes = commentVotes;
    }

    public void addPost(Post post) {
        this.post.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.post.remove(post);
        post.setUser(null);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setUser(null);
    }

    public void addLike(PostVote postVote) {
        this.likes.add(postVote);
        postVote.setUserId(this);
    }

    public void removeLike(PostVote postVote) {
        this.likes.remove(postVote);
        postVote.setUserId(null);
    }

    public void addCommentVote(CommentVote commentVote) {
        this.commentVotes.add(commentVote);
        commentVote.setUserId(this);
    }

    public void removeCommentVote(CommentVote commentVote) {
        this.commentVotes.remove(commentVote);
        commentVote.setUserId(null);
    }

}