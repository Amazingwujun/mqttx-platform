package com.jun.mqttxplatform.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {

    private Integer id;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "1[0-9]{10}",message = "手机号码格式错误")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$",message = "密码格式异常")
    private String password;

    private String nickname;

    private String avatar;

    private String email;
}
