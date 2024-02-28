package com.hnust.repository;

import com.hnust.pojo.Blog;

import java.util.List;

public interface BlogMapper {
    /**
     * 文章保存
     * @param blog
     * @return
     */
    Integer saveBlog(Blog blog);

    /**
     * 查询所有审批通过的文章
     * @return
     */
    List<Blog> selectAllAdoptArticleStep();

    List<Blog> selectArticleByBlogIds(Long[] blogIds);
}
