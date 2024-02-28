package com.hnust.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;


public interface ArticleProcessingService {

    /**
     * 文章保存
     * @param title
     * @param text
     * @param labels
     * @param briefIntroduction
     * @param type
     * @param session
     * @return
     */
    public Boolean articleStorage(String title, MultipartFile text, String[] labels, String briefIntroduction, String type, HttpSession session);

    /**
     * 分页查询
     * @param pageNum
     */
    void selectAllByPageNum(HttpSession session, Integer pageNum, Long categoryId);

    /**
     * 查询文章详情
     * @param session
     * @param articleIndex
     */
    void selectArticleDetails(HttpSession session,Integer articleIndex);


}
