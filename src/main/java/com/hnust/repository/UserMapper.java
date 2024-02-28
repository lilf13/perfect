package com.hnust.repository;

import com.hnust.pojo.User;

public interface UserMapper {
    Integer saveUser(User user);

    User selectByEmail(String email);

    Integer updateUser(User user);

    User selectByUserId(Long userId);
}
