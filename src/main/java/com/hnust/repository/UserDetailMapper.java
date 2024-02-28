package com.hnust.repository;

import com.hnust.pojo.UserDetail;

public interface UserDetailMapper {

    Integer saveUserDetail(UserDetail userDetail);

    UserDetail selectByUserId(Long userId);
}
