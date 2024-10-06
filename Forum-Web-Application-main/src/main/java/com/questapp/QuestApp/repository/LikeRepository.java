package com.questapp.QuestApp.repository;

import com.questapp.QuestApp.model.Comment;
import com.questapp.QuestApp.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findByUserIdAndPostId(Optional<Long> userId, Optional<Long> postId);

    List<Like> findByUserId(Optional<Long> userId);

    List<Like> findByPostId(Optional<Long> postId);

    @Query(value = "SELECT * FROM p_like WHERE post_id IN (:postIds) LIMIT 5;", nativeQuery = true)
    List<Like> findUserLikesByPostId(@Param("postIds") List<Long> postIds);
}
