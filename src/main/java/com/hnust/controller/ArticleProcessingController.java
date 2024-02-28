package com.hnust.controller;

import com.hnust.service.ArticleProcessingService;
import com.hnust.storage.ArticleImgStorage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/forum/blog")
public class ArticleProcessingController {

    @Resource
    ArticleProcessingService articleProcessingService;
    @Resource
    ArticleImgStorage articleImgStorage;


    /**
     * 文章发布
     * @param title
     * @param text
     * @param labels
     * @param briefIntroduction
     * @param type
     * @param session
     * @return
     */
    @PostMapping("/storage")
    @ResponseBody
    public String storage( @RequestParam("title") String title,
                           @RequestParam("text") MultipartFile text,
                           @RequestParam("label") String[] labels,
                           @RequestParam("briefIntroduction") String briefIntroduction,
                           @RequestParam("type") String type,
                           HttpSession session){
        return articleProcessingService.articleStorage(title, text, labels, briefIntroduction, type, session).toString();
    }

    /**
     * 文章图片保存
     * @param imgFile
     * @param session
     * @return
     */
    @PostMapping("/articleImg")
    @ResponseBody
    public String articleImg(@RequestParam("wangeditor-uploaded-image") MultipartFile imgFile,
                             HttpSession session) {
        return articleImgStorage.savingArticleImages(imgFile,session);
    }

    /**
     * 文章分页
     * @return
     */
    @GetMapping("/article/{pageNum}/{categoryId}")
    public String toArticle(@PathVariable("pageNum") Integer pageNum,
                            @PathVariable("categoryId") Long categoryId,
                            HttpSession session) {
        articleProcessingService.selectAllByPageNum(session,pageNum,categoryId);
        return "article";
    }

    /**
     * 文章详情
     * @param articleIndex
     * @param session
     * @return
     */
    @GetMapping("/articleDetail/{articleIndex}")
    public String toArticleDetails(@PathVariable("articleIndex") Integer articleIndex,
                                   HttpSession session) {
        articleProcessingService.selectArticleDetails(session,articleIndex);
        return "articleDetail";
    }

}
