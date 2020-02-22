package com.shfe.community.dto;

import com.shfe.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title, description,tag;
    private Long gmtCreate,gmtModified;
    private Integer creator;
    private Integer commentCount,viewCount,likeCount;
    private User user;
}
