package com.hnust.interceptor;

import com.hnust.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class WorkshopInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session
        HttpSession session = request.getSession();
        //获取user对象
        User user = (User) session.getAttribute("user");
        if (user.getId() == null) {
            //重定向到文章页面
            response.sendRedirect("/forum/blog/article/1/0");
            return false;
        }
        return true;
    }

}
