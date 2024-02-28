package com.hnust.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 博客标签关系类
 * @Author E
 * @Date 2023/5/19 23:07
 * @Version 1.0
 * @Since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogTag {
    private Long blogId;
    private Long tagId;
    private Tag tag;
    private List<Blog> blogs;

    public BlogTag(Long blogId, Long tagId) {
        this.blogId = blogId;
        this.tagId = tagId;
    }
}
