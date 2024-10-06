package com.questapp.QuestApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name="post")
@Data
public class Post {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            Long id;

    @ManyToOne(fetch = FetchType.EAGER) // One user has many posts.
            @JoinColumn(name = "user_id", nullable = false)
            @OnDelete(action = OnDeleteAction.CASCADE) // If the user is deleted, delete all related posts.
            User user;

    String title;
    @Lob
    @Column(columnDefinition = "text")
    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;
}




