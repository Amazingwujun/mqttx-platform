package com.jun.mqttxplatform.entity.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Topic {

    private Integer id;

    private String name;

    private String remark;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}