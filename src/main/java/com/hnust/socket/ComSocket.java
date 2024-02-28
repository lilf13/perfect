package com.hnust.socket;

import com.alibaba.fastjson.JSON;
import com.hnust.pojo.Com;
import com.hnust.repository.ComMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Component
@ServerEndpoint(value = "/forum/socket/commentSocket")
public class ComSocket {

    private static ComMapper comMapper;

    private static Map<Long, Set<Session>> mapSession = new HashMap<>();

    /**
     * 注入依赖bean
     * @param context
     */
    public static void setApplicationContext(ApplicationContext context) {
        comMapper = context.getBean("comMapper", ComMapper.class);
        System.out.println(comMapper);
    }

    @OnOpen
    public void onOpen(Session session) {
        // 处理WebSocket连接打开事件
        System.out.println("连接已建立!");
    }

    @OnClose
    public void onClose(Session session) {
        // 处理WebSocket连接关闭事件
        for (Map.Entry<Long, Set<Session>> entry : mapSession.entrySet()) {
            Set<Session> entryValue = entry.getValue();
            entryValue.remove(session);
        }
        System.out.println("连接已关闭...");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // 处理WebSocket错误事件
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
        //获取当前文章id
        Long blogId = Long.parseLong(message);
        //从map集合中获取set集合
        Set<Session> sessionsBySession = mapSession.get(blogId);
        if (sessionsBySession == null) {
            Set<Session> set = new HashSet<>();
            set.add(session);
            mapSession.put(blogId, set);
        } else {
            sessionsBySession.add(session);
        }
        //查询评论数
        Long numberOfComments = comMapper.selectNumberOfComments(blogId);
        System.out.println("评论数：");
        System.out.println(numberOfComments);
        //将评论查出来
        List<Com> comments = comMapper.selectHighQualityCommentsStep(blogId);
        System.out.println(comments);
        if (comments.size() > 0) {
            comments.get(0).setCount(numberOfComments);
        }
        String jsonString = JSON.toJSONString(comments);
        System.out.println(jsonString);
        // 发送 JSON 字符串给客户端
        try {
            session.getBasicRemote().sendText(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendUpdateToClients(Com comment) {
        System.out.println("——————————————————————————————————");
        List<Com> comments = new ArrayList<>();
        comments.add(comment);
        String jsonString = JSON.toJSONString(comments);
        System.out.println(jsonString);
        //获取session集合
        Set<Session> sessions = mapSession.get(comment.getBlogId());
        for (Session session : sessions) {
            // 发送 JSON 字符串给每个客户端
            try {
                session.getBasicRemote().sendText(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}