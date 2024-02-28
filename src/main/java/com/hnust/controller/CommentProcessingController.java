package com.hnust.controller;

import com.alibaba.fastjson.JSON;
import com.hnust.pojo.Com;
import com.hnust.service.CommentProcessingService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/forum/com")
public class CommentProcessingController {

    @Resource
    CommentProcessingService commentProcessingService;

    /**
     * 评论发布
     * @param comment
     * @param session
     */
    @ResponseBody
    @PostMapping("/release")
    public void release(@RequestParam("parentComId") Long parentComId,
                        @RequestParam("comment") String comment,
                        HttpSession session) {
        commentProcessingService.commentPosting(session,parentComId,comment);
    }

    @ResponseBody
    @GetMapping(value = "/reply/{parentComId}",produces = "application/text; charset=UTF-8")
    public String reply(@PathVariable("parentComId") Long parentComId) {
        List<Com> comList = commentProcessingService.replyToDisplay(parentComId);
        String comJsonStr = JSON.toJSONString(comList);
        System.out.println(comJsonStr);
        return comJsonStr;
    }


}
