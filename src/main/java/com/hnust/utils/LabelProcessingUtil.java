package com.hnust.utils;

import com.hnust.pojo.Blog;
import com.hnust.pojo.BlogTag;
import com.hnust.pojo.Tag;
import com.hnust.repository.TagMapper;

import java.util.Arrays;
import java.util.List;

public class LabelProcessingUtil {

    /**
     * 对前端传过来的博客标签进行处理，查询它们的id和博客id封装成博客标签关系对象
     * @param labels
     * @param blog
     * @param tagMapper
     * @return
     */
    public static BlogTag[] handle(String[] labels, Blog blog, TagMapper tagMapper) {
        //获取前端传过来的标签集
        //查询标签集的id
        List<Tag> tags = tagMapper.selectByTagName(labels);
        //博客标签关系对象数组
        BlogTag[] blogTags = new BlogTag[labels.length];
        //获取博客id
        Long blogId = blog.getId();
        //执行博客标签关系表的插入操作
        for (int i=0;i<tags.size();i++) {
            blogTags[i] = new BlogTag(blogId, tags.get(i).getId());
        }
        return blogTags;
    }
}
