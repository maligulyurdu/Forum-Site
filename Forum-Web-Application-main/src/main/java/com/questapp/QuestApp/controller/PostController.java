package com.questapp.QuestApp.controller;

import com.questapp.QuestApp.model.Post;
import com.questapp.QuestApp.request.PostCreateRequest;
import com.questapp.QuestApp.response.PostResponse;
import com.questapp.QuestApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }
    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable Long postId){
        return postService.getOnePostById(postId);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostCreateRequest updatedPost){
        return postService.updateOnePostById(postId, updatedPost);
    }

    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable Long postId){
        postService.deleteOnePostById(postId);
    }
}
