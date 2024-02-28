package com.hnust.repository;

import com.hnust.pojo.BlogTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogTagMapper {
    /**
     * 插入操作
     * @param blogTags
     * @return
     */
    Integer saveBolgTag(@Param("blogTags") BlogTag[] blogTags);

    /**
     * 联机查询标签名
     * @param blogId
     * @return
     */
    List<BlogTag> selectByBlogIdStep(Long blogId);

    /**
     * 通过标签ID查询文章ID
     * @param tagId
     * @return
     */
    List<Long> selectByTagId(Long tagId);
}
