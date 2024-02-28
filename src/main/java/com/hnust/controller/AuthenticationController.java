package com.hnust.controller;

import com.hnust.service.AuthenticationService;
import com.hnust.utils.VerificationCodeUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 身份验证控制器
 */
@Controller
@RequestMapping("/forum/validate")
public class AuthenticationController {

    @Resource
    AuthenticationService authenticationService;

    /**
     * 验证码发送
     * @param email
     * @return
     */
    @ResponseBody
    @GetMapping("/verificationCode")
    public String verificationCodeAcquisition(@RequestParam("email") String email) {
        /*发送*/
        return VerificationCodeUtil.send(email);
    }

    /**
     * 验证码方式进入
     * @return
     */
    @ResponseBody
    @PostMapping("/vcLogin")
    public String verificationCodeEntry(@RequestParam("mailbox") String mailbox,
                                        HttpSession session) {
        return authenticationService.verificationCodeEntry(mailbox,session).toString();
    }

}