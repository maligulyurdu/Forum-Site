package com.questapp.QuestApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data // This annotation makes getter/setters automatically.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;

}
