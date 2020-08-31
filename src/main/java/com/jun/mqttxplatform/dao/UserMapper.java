package com.jun.mqttxplatform.dao;

import com.jun.mqttxplatform.entity.po.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    int insertSelective(User record);

    /**
     * 通过手机号获取用户.
     * 方法加了排它锁，请注意
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    User selectByMobile(String mobile);

    int updateByPrimaryKeySelective(User record);

    List<User> getUserList();
}