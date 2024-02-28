package com.hnust.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客类
 * @Author E
 * @Date 2023/5/18 21:41
 * @Version 1.0
 * @Since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private Long id;
    private Long userId;
    private String title;
    private String contentPath;
    private String introduce;
    private String type;
    private String approvalStatus;
    private LocalDateTime publishDate;
    private LocalDateTime lastEditDate;
    private String mainTextOfTheArticle;//文章正文
    private List<Tag> tags;
    private User user;//用户对象

    public Blog(Long id, Long userId, String title, String contentPath, String introduce, String type, String approvalStatus, LocalDateTime publishDate, LocalDateTime lastEditDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contentPath = contentPath;
        this.introduce = introduce;
        this.type = type;
        this.approvalStatus = approvalStatus;
        this.publishDate = publishDate;
        this.lastEditDate = lastEditDate;
    }

}
