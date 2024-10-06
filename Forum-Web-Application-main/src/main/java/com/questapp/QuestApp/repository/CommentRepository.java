package com.questapp.QuestApp.repository;

import com.questapp.QuestApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {
    List<Comment> findByUserIdAndPostId(Long userId, Long postId);

    List<Comment> findByUserId(Long userId);

    List<Comment> findByPostId(Long postId);

    @Query(value = "SELECT * FROM comment WHERE post_id IN (:postIds) LIMIT 5;\n", nativeQuery = true)
    List<Comment> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);
}


