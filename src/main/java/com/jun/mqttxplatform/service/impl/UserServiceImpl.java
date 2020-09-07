package com.jun.mqttxplatform.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.jun.mqttxplatform.constants.ResponseCode;
import com.jun.mqttxplatform.dao.UserMapper;
import com.jun.mqttxplatform.entity.Response;
import com.jun.mqttxplatform.entity.dto.UserDTO;
import com.jun.mqttxplatform.entity.po.User;
import com.jun.mqttxplatform.entity.vo.UserVO;
import com.jun.mqttxplatform.service.IUserService;
import com.jun.mqttxplatform.utils.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private Algorithm algorithm;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, Algorithm algorithm) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.algorithm = algorithm;
    }

    @Override
    public Response<UserVO> signIn(UserDTO userDTO) {
        String mobile = userDTO.getMobile();
        User user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return Response.fail("用户不存在");
        }
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return Response.fail("用户名或密码错误");
        }

        //获取用户权限
        List<String> userPermissions = userMapper.selectUserPermissions(user.getId());

        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .mobile(user.getMobile())
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .token(genToken(user.getId(), null, userPermissions))
                .build();
        return Response.ok(userVO);
    }

    @Override
    public Response<UserVO> signUp(UserDTO userDTO) {
        if (StringUtils.isEmpty(userDTO.getNickname())) {
            userDTO.setNickname("悟空");
        }

        User user = BeanUtils.bean2bean(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userMapper.insertSelective(user) > 0 ?
                Response.ok() : Response.fail(ResponseCode.DUPLICATE_DATA_ERR.getCode(), "手机号已被注册");
    }

    @Override
    public Response<List<UserVO>> getUserList() {
        List<User> userList = userMapper.getUserList();
        List<UserVO> voList = userList.stream()
                .map(user -> UserVO.builder()
                        .id(user.getId())
                        .nickname(user.getNickname())
                        .avatar(user.getAvatar())
                        .mobile(user.getMobile())
                        .email(user.getEmail())
                        .createTime(user.getCreateTime())
                        .build()
                )
                .collect(Collectors.toList());

        return Response.ok(voList);
    }

    /**
     * 生成令牌
     *
     * @param userId      客户ID
     * @param roles       角色列表
     * @param permissions 权限列表
     * @return JWT
     */
    private String genToken(Integer userId, List<String> roles, List<String> permissions) {
        JWTCreator.Builder builder = JWT.create()
                .withIssuer("mqttx")
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000));
        if (!CollectionUtils.isEmpty(roles)) {
            builder.withClaim("roles", roles);
        }
        if (!CollectionUtils.isEmpty(permissions)) {
            builder.withClaim("permissions", permissions);
        }
        return builder.sign(algorithm);
    }
}
