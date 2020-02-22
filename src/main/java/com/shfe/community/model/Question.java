package com.shfe.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title, description,tag;
    private Long gmtCreate,gmtModified;
    private Integer creator;
    private Integer commentCount,viewCount,likeCount;
    private String avatarUrl;
}
