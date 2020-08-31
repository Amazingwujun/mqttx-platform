package com.jun.mqttxplatform.service;


import com.jun.mqttxplatform.entity.Response;
import com.jun.mqttxplatform.entity.dto.UserDTO;
import com.jun.mqttxplatform.entity.vo.UserVO;

import java.util.List;

public interface IUserService {

    Response<UserVO> signIn(UserDTO userDTO);

    Response<UserVO> signUp(UserDTO userDTO);

    Response<List<UserVO>> getUserList();
}
