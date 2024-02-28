package com.hnust.service;

import com.hnust.pojo.Com;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CommentProcessingService {

    /**
     * 评论发布
     * @param comment
     * @return
     */
    Boolean commentPosting(HttpSession session,Long parentComId,String comment);

    List<Com> replyToDisplay(Long parentComId);

}
