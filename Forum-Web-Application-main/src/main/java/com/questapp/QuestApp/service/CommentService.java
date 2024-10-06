package com.questapp.QuestApp.service;

import com.questapp.QuestApp.model.Comment;
import com.questapp.QuestApp.model.Post;
import com.questapp.QuestApp.model.User;
import com.questapp.QuestApp.repository.CommentRepository;
import com.questapp.QuestApp.request.CommentCreateRequest;
import com.questapp.QuestApp.request.CommentUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent()){
            return commentRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            return commentRepository.findByPostId(postId.get());
        }
        else
            return commentRepository.findAll();
    }


    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user = userService.getOneUser(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if(user!=null && post!=null){
            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        }
        return null;
    }

    public Comment updateOneComment(Long commentId, CommentUpdateRequest updatedComment) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(updatedComment.getText());
            return commentRepository.save(commentToUpdate);
        }
        else return null;
    }

    public void deleteOneCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
