package com.questapp.QuestApp.controller;

import com.questapp.QuestApp.model.Comment;
import com.questapp.QuestApp.request.CommentCreateRequest;
import com.questapp.QuestApp.request.CommentUpdateRequest;
import com.questapp.QuestApp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> userId,
                                        @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId,postId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneComment(request);
    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId,
                                    @RequestBody CommentUpdateRequest updatedComment){
        return commentService.updateOneComment(commentId,updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOneCommentById(commentId);
    }


}
