package com.hnust.service;


import jakarta.servlet.http.HttpSession;

public interface AuthenticationService {

    /**
     * 验证码登录验证
     * @param email
     * @param session
     * @return
     */
    Boolean verificationCodeEntry(String email, HttpSession session);
}
