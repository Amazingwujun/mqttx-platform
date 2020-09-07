package com.jun.mqttxplatform.controller;

import com.github.pagehelper.PageInfo;
import com.jun.mqttxplatform.constants.Permissions;
import com.jun.mqttxplatform.entity.Response;
import com.jun.mqttxplatform.entity.dto.TopicDTO;
import com.jun.mqttxplatform.entity.vo.TopicVO;
import com.jun.mqttxplatform.service.ITopicService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 订阅发布接口
 */
@RestController
public class TopicController {

    private ITopicService topicService;

    public TopicController(ITopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/topic")
    @PreAuthorize("hasAuthority(\"" + Permissions.ADMIN_CREATE + "\")")
    public Response<Void> createTopic(@RequestBody @Valid TopicDTO topicDTO) {

        return topicService.createTopic(topicDTO);
    }

    @GetMapping("/topics")
    @PreAuthorize("hasAuthority(\"" + Permissions.ADMIN_QUERY + "\")")
    public Response<PageInfo<TopicVO>> getAllTopics(Pageable pageable) {
        return topicService.getAllTopics(pageable);
    }

    @PutMapping("/topic/{id:[1-9][0-9]*}")
    @PreAuthorize("hasAuthority(\"" + Permissions.ADMIN_UPDATE + "\")")
    public Response<Void> updateTopic(@PathVariable Integer id, String remark) {
        return topicService.updateTopic(id, remark);
    }

    @DeleteMapping("/topic/{id:[1-9][0-9]*}")
    @PreAuthorize("hasAuthority(\"" + Permissions.ADMIN_DELETE + "\")")
    public Response<Void> deleteTopic(@PathVariable Integer id) {
        return topicService.deleteTopic(id);
    }
}
