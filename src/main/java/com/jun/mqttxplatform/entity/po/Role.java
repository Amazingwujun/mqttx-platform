package com.jun.mqttxplatform.entity.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Role {

    private String name;

    private Integer id;

    private LocalDateTime creatTime;

    private LocalDateTime updateTime;
}
