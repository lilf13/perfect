package com.hnust.controller;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * html页面控制器
 */
@Controller
@RequestMapping("/forum/view")
public class ViewController {

    @GetMapping("/workshop")
    public String toWorkshop() {
        return "workshop";
    }

    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/success")
    public String toSuccess() {
        return "success";
    }

    @GetMapping("/user")
    public String toUser() {
        return "user";
    }

}
