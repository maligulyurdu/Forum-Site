package com.questapp.QuestApp.service;

import com.questapp.QuestApp.model.Like;
import com.questapp.QuestApp.model.Post;
import com.questapp.QuestApp.model.User;
import com.questapp.QuestApp.repository.LikeRepository;
import com.questapp.QuestApp.repository.PostRepository;
import com.questapp.QuestApp.request.PostCreateRequest;
import com.questapp.QuestApp.response.LikeResponse;
import com.questapp.QuestApp.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;



    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Autowired
    private LikeService likeService;




    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if (userId.isPresent()) {
            list =  postRepository.findByUserId(userId.get());
        }
        list = postRepository.findAll();
        return list.stream().map(p ->{
            List<LikeResponse> likes = likeService.getAllLikesByParam(Optional.ofNullable(null), Optional.of(p.getId()));
            return new PostResponse(p, likes); }).collect(Collectors.toList());
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUser(newPostRequest.getUserId());
        if (user == null) {
            return null;
        } else {
            Post toSave = new Post();
            toSave.setId(newPostRequest.getId());
            toSave.setText(newPostRequest.getText());
            toSave.setTitle(newPostRequest.getTitle());
            toSave.setUser(user);
            toSave.setCreateDate(new Date());
            return postRepository.save(toSave);
        }
    }

    public Post updateOnePostById(Long postId, PostCreateRequest updatedPost){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate = post.get();
            toUpdate.setText(updatedPost.getText());
            toUpdate.setTitle(updatedPost.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        else return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}