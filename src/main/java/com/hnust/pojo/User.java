package com.hnust.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户基本信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;
    private String role;
    private String status;
    private UserDetail userDetail;

    public User(Long id, String email, String password, LocalDateTime registrationDate, LocalDateTime lastLoginDate, String role, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.lastLoginDate = lastLoginDate;
        this.role = role;
        this.status = status;
    }
}
