package com.questapp.QuestApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name="comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY) // One post has many comments.
        @JoinColumn(name = "post_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE) // If the user is deleted, delete all related posts.
        @JsonIgnore
        Post post;

    @ManyToOne(fetch = FetchType.LAZY) // One user has many comments.
        @JoinColumn(name = "user_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE) // If the user is deleted, delete all related posts.
        @JsonIgnore
        User user;

    @Lob
    @Column(columnDefinition = "text")
    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;
}
