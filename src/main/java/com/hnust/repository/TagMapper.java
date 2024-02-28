package com.hnust.repository;

import com.hnust.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    /**
     * 通过标签名字查询标签id
     * @param names
     * @return
     */
    List<Tag> selectByTagName(@Param("names") String[] names);

    /**
     * 通过标签id查标签名
     * @param tagId
     * @return
     */
    Tag selectByTagId(Long tagId);
}
