package com.hnust.filter;

import com.hnust.pojo.User;
import com.hnust.pojo.UserDetail;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 创建一个空的user对象存入session域中，防止thymeleaf解析出错
 */
public class InitUserSessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        /*从session域判断该用户是否已登录*/
        if (session.getAttribute("user") == null) {
            User user = new User();
            UserDetail userDetail = new UserDetail();
            userDetail.setAvatarPath("../../../../img/localImg/login.png");
            user.setUserDetail(userDetail);
            session.setAttribute("user",user);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
