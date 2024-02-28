package com.hnust.service.imple;


import com.hnust.pojo.User;
import com.hnust.pojo.UserDetail;
import com.hnust.repository.UserDetailMapper;
import com.hnust.repository.UserMapper;
import com.hnust.service.AuthenticationService;
import com.hnust.utils.CharacterGenerationUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 登录/注册
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserDetailMapper userDetailMapper;

    @Transactional
    @Override
    public Boolean verificationCodeEntry(String email, HttpSession session) {
        //先确定该邮箱是否已经注册
        User userPreviously = userMapper.selectByEmail(email);
        //该邮箱已经注册的话直接登录
        if (userPreviously != null) {
            //更新用户最后一次登录时间
            userPreviously.setLastLoginDate(LocalDateTime.now());
            int i = userMapper.updateUser(userPreviously);
            //存入session域中
            session.setAttribute("user",userPreviously);
            return true;
        }
        User userNow = new User(null,email,null, LocalDateTime.now(), LocalDateTime.now(), "用户", "启用");
        //进行注册操作
        int i = userMapper.saveUser(userNow);
        //初步补充详细信息表
        UserDetail userDetail = new UserDetail(null, userNow.getId(), CharacterGenerationUtil.generateRandomEightCharacterString(), "../../../../img/headImg/defaultHeadImg.jpg", null, null, null, null);
        i += userDetailMapper.saveUserDetail(userDetail);
        //存入session域中
        userNow.setUserDetail(userDetail);
        session.setAttribute("user",userNow);
        return true;
    }
}
