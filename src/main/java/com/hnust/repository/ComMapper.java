package com.hnust.repository;

import com.hnust.pojo.Com;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ComMapper {

    /**
     * 评论保存
     * @param com
     * @return
     */
    Integer saveComment(Com com);

    /**
     * 查询优质评论通过文章id
     * @return
     */
    List<Com> selectHighQualityCommentsStep(@Param("blogId") Long blogId);

    /**
     * 查询二级评论
     * @param parentComId
     * @return
     */
    List<Com> selectReplyByParentComId(@Param("parentComId") Long parentComId);

    /**
     * 查询评论通过ID
     * @param comId
     * @return
     */
    Com selectByComId(@Param("comId") Long comId);

    /**
     * 更新评论(一级评论数)
     * @param com
     * @return
     */
    Integer updateCom(Com com);

    /**
     * 查询评论数
     * @param blogId
     * @return
     */
    Long selectNumberOfComments(@Param("blogId") Long blogId);
}
