package com.questapp.QuestApp.service;

import com.questapp.QuestApp.model.Like;
import com.questapp.QuestApp.model.Post;
import com.questapp.QuestApp.model.User;
import com.questapp.QuestApp.repository.LikeRepository;
import com.questapp.QuestApp.request.LikeCreateRequest;
import com.questapp.QuestApp.response.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesByParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list = likeRepository.findByUserIdAndPostId(userId,postId);
        } else if (userId.isPresent()) {
            list = likeRepository.findByUserId(userId);
        } else if (postId.isPresent()) {
            list = likeRepository.findByPostId(postId);
        }
        else list = likeRepository.findAll();

        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUser(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if(user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setId(request.getId());
            likeToSave.setPost(post);
            likeToSave.setUser(user);
            return likeRepository.save(likeToSave);
        }
        else
            return null;
    }

    public Like getOneLikeById(Long LikeId) {
        return likeRepository.findById(LikeId).orElse(null);
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }


}
