package com.questapp.QuestApp.service;

import com.questapp.QuestApp.model.Comment;
import com.questapp.QuestApp.model.Like;
import com.questapp.QuestApp.model.User;
import com.questapp.QuestApp.repository.CommentRepository;
import com.questapp.QuestApp.repository.LikeRepository;
import com.questapp.QuestApp.repository.PostRepository;
import com.questapp.QuestApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    LikeRepository likeRepository;
    CommentRepository commentRepository;
    PostRepository postRepository;

    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){ // If we have this user...
            User foundUser = user.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(newUser);
            return foundUser;
        }
        else
            return null;
    }

    public void deleteOneUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if(postIds.isEmpty()){
            return null;
        }
        List<Comment> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Like> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
