package com.questapp.QuestApp.repository;

import com.questapp.QuestApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId); // findBy is a mould. After, Spring will make everything ok for us.

    @Query(value = "SELECT id FROM post WHERE user_id = :userId ORDER BY create_date DESC LIMIT 5;\n", nativeQuery = true)
    List<Long> findTopByUserId(@Param("userId") Long userId);
}
