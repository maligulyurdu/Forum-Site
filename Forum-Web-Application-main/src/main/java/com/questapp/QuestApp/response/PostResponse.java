package com.questapp.QuestApp.response;

import com.questapp.QuestApp.model.Like;
import com.questapp.QuestApp.model.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    Long id;
    Long userId;
    String username;
    String title;
    String text;
    List<LikeResponse> postLikes;

    public PostResponse(Post entity, List<LikeResponse> likes){ // Mapper
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.postLikes = likes;
    }

}


