package com.jun.mqttxplatform.controller;

import com.jun.mqttxplatform.entity.Response;
import com.jun.mqttxplatform.entity.dto.UserDTO;
import com.jun.mqttxplatform.entity.vo.UserVO;
import com.jun.mqttxplatform.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signIn")
    public Response<UserVO> signIn(@RequestBody UserDTO userDTO) {
        return userService.signIn(userDTO);
    }

    @PostMapping("/signUp")
    public Response<UserVO> signUp(@RequestBody UserDTO userDTO) {
        return userService.signUp(userDTO);
    }

    @GetMapping("/userList")
    public Response<List<UserVO>> getUserList() {
        return userService.getUserList();
    }
}
