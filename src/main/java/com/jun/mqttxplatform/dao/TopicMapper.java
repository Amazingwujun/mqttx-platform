package com.jun.mqttxplatform.dao;

import com.jun.mqttxplatform.entity.po.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicMapper {

    int insertSelective(Topic record);

    Topic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Topic record);

    int deleteById(Integer id);

    List<Topic> selectAll();
}