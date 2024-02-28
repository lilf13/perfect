package com.hnust.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Com {
    private Long id;
    private Long userId;
    private Long blogId;
    private Long parentComId;
    private String content;
    private Long likesCount;
    private Long repliesCount;
    private LocalDateTime releaseDate;
    private User user;
    private Integer commentLevel;
    private Long count;//用于展示评论数，无实际作用

    public Com(Long id, Long userId, Long blogId, Long parentComId, String content, Long likesCount, Long repliesCount, LocalDateTime releaseDate) {
        this.id = id;
        this.userId = userId;
        this.blogId = blogId;
        this.parentComId = parentComId;
        this.content = content;
        this.likesCount = likesCount;
        this.repliesCount = repliesCount;
        this.releaseDate = releaseDate;
    }
}
