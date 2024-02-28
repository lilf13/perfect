package com.hnust.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    private Long id;
    private Long userId;
    private String userName;
    private String avatarPath;
    private String bio;
    private Character gender;
    private String address;
    private String phone;
}
