package com.jun.mqttxplatform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jun.mqttxplatform.constants.ResponseCode;
import com.jun.mqttxplatform.dao.TopicMapper;
import com.jun.mqttxplatform.entity.Response;
import com.jun.mqttxplatform.entity.dto.TopicDTO;
import com.jun.mqttxplatform.entity.po.Topic;
import com.jun.mqttxplatform.entity.vo.TopicVO;
import com.jun.mqttxplatform.service.ITopicService;
import com.jun.mqttxplatform.utils.BeanUtils;
import com.jun.mqttxplatform.utils.TopicUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements ITopicService {

    private TopicMapper topicMapper;

    public TopicServiceImpl(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    @Override
    public Response<Void> createTopic(TopicDTO topicDTO) {
        String topic = topicDTO.getName();
        if (!TopicUtils.isValid(topic)) {
            return Response.fail(ResponseCode.BAD_REQUEST.getCode(), "无效的 topic: " + topic);
        }

        return topicMapper.insertSelective(BeanUtils.bean2bean(topicDTO, Topic.class)) > 0 ?
                Response.ok() :
                Response.fail(ResponseCode.ACCESS_FORBIDDEN);
    }

    @Override
    public Response<PageInfo<TopicVO>> getAllTopics(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());

        List<TopicVO> collect = topicMapper.selectAll().stream()
                .map(topic -> BeanUtils.bean2bean(topic, TopicVO.class))
                .collect(Collectors.toList());

        PageInfo<TopicVO> pageInfo = new PageInfo<>(collect);
        return Response.ok(pageInfo);
    }


    @Override
    public Response<Void> deleteTopic(Integer id) {
        return topicMapper.deleteById(id) > 0 ?
                Response.ok() :
                Response.fail(ResponseCode.INFO_NOT_FOUND_ERR.getCode(), "对象不存在");
    }

    @Override
    public Response<Void> updateTopic(Integer id, String remark) {
        Topic topic = new Topic();
        topic.setId(id);
        topic.setRemark(remark);
        return topicMapper.updateByPrimaryKeySelective(topic) > 0 ?
                Response.ok() :
                Response.fail(ResponseCode.SERVICE_BUSY_ERR);
    }
}
