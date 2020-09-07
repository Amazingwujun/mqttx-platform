package com.jun.mqttxplatform.service;

import com.github.pagehelper.PageInfo;
import com.jun.mqttxplatform.entity.Response;
import com.jun.mqttxplatform.entity.dto.TopicDTO;
import com.jun.mqttxplatform.entity.vo.TopicVO;
import org.springframework.data.domain.Pageable;

public interface ITopicService {

    Response<Void> createTopic(TopicDTO topicDTO);

    Response<PageInfo<TopicVO>> getAllTopics(Pageable pageable);

    Response<Void> deleteTopic(Integer id);

    Response<Void> updateTopic(Integer id, String remark);
}
