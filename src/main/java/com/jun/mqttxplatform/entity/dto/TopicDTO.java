package com.jun.mqttxplatform.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TopicDTO {

    @NotBlank(message = "topic 名称不能为空")
    private String name;

    @NotBlank(message = "topic 说明不能为空")
    private String remark;
}
