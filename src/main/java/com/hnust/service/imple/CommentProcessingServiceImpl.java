package com.hnust.service.imple;

import com.hnust.pojo.Blog;
import com.hnust.pojo.Com;
import com.hnust.pojo.User;
import com.hnust.repository.ComMapper;
import com.hnust.service.CommentProcessingService;
import com.hnust.socket.ComSocket;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.jdbc.Null;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class CommentProcessingServiceImpl implements CommentProcessingService {

    @Resource
    ComMapper comMapper;

    @Transactional
    @Override
    public Boolean commentPosting(HttpSession session,Long parentComId,String comment) {
        //先从session域中获取当前文章ID和用户ID
        Blog blog = (Blog) session.getAttribute("blog");
        User user = (User) session.getAttribute("user");
        if (parentComId == 0) {
            parentComId = null;
        } else {
            //查询出该一级评论的评论数并更新
            Com com = comMapper.selectByComId(parentComId);
            com.setRepliesCount(com.getRepliesCount()+1);
            //更新
            comMapper.updateCom(com);
            com.setCommentLevel(2);
            ComSocket.sendUpdateToClients(com);
        }
        Com com = new Com(null, user.getId(), blog.getId(), parentComId,comment,0l,0l,LocalDateTime.now());
        Integer i = comMapper.saveComment(com);
        if (i < 1) {
            return false;
        }
        //如果是对文章进行评论
        if (parentComId == null) {
            //刷新一次评论显示中心
            com.setUser(user);
            com.setCommentLevel(1);
            ComSocket.sendUpdateToClients(com);
        }
        return true;
    }

    @Override
    public List<Com> replyToDisplay(Long parentComId) {
        return comMapper.selectReplyByParentComId(parentComId);
    }
}
