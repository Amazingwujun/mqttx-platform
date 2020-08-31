package com.jun.mqttxplatform.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jun.mqttxplatform.entity.po.Permission;
import com.jun.mqttxplatform.entity.po.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {

    private Integer id;

    private String mobile;

    private String token;

    private String nickname;

    private String avatar;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<Role> roles;

    private List<Permission> permissions;
}
