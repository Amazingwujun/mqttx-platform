package com.jun.mqttxplatform.entity.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;

    private String mobile;

    private String password;

    private String avatar;

    private String nickname;

    private String email;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}