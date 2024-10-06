package com.questapp.QuestApp.repository;

import com.questapp.QuestApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
}
